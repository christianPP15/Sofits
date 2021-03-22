package com.example.sofits_frontend.ui.Registro

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.Login.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import javax.inject.Inject

class RegistroActivity : AppCompatActivity() {
    private var button: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    @Inject lateinit var registerViewModel: RegistroViewModel
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_registro)

        var choose : Button = findViewById(R.id.button_choose_Imagen)
        choose!!.setOnClickListener{ showPictureDialog() }
        val botonRegistro= findViewById<Button>(R.id.buton_register)
        botonRegistro.setOnClickListener {
            val registerData:RegisterRequest?= sendRegisterInfo()
            if(registerData!=null){
                var registerDataResponse: RegisterResponse?
                registerViewModel.doRegisterComplete(registerData)
                registerViewModel.registerData.observe(this, Observer { response->
                    when(response) {
                        is Resource.Success -> {
                            registerDataResponse=response.data
                            if (registerDataResponse!=null){
                                val user=hashMapOf(
                                    "nombre" to registerDataResponse!!.user.nombre
                                )
                                db.collection("users")
                                    .document(registerDataResponse!!.user.id)
                                    .set(user)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d("NEWUSERFIREBASE", "DocumentSnapshot added correctly")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("NEWUSERFIREBASE", "Error adding document", e)
                                    }
                                val shared = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
                                with(shared.edit()) {
                                    putString(getString(R.string.TOKEN_USER), registerDataResponse!!.token)
                                    putString(getString(R.string.TOKEN_REFRESCO),registerDataResponse!!.refreshToken)
                                    putString(getString(R.string.IdentificadorUsuario),registerDataResponse!!.user.id)
                                    commit()
                                }
                                val navegar = Intent(this, MainActivity::class.java)
                                startActivity(navegar)
                            }
                        }
                        is Resource.Error -> {
                            val toast= Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG)
                            toast.show()
                        }
                    }
                })
            }
        }
    }


    private fun sendRegisterInfo(): RegisterRequest? {
        val emailInput = findViewById<TextView>(R.id.input_register_email)
        val nombreUsuario = findViewById<TextView>(R.id.input_nombre_registro)
        val password = findViewById<TextView>(R.id.input_password_register)
        val passwordRepeat= findViewById<TextView>(R.id.input_password_repeat_register)
        val fecha= findViewById<TextView>(R.id.input_fecha_registro)
        if (emailInput.text.isNotBlank() && nombreUsuario.text.isNotBlank() && password.text.isNotBlank() && passwordRepeat.text.isNotBlank() && fecha.text.isNotBlank()){
            if (password.text.toString()==passwordRepeat.text.toString()){
                return RegisterRequest(emailInput.text.toString(),
                    nombreUsuario.text.toString(),password.text.toString(),
                    passwordRepeat.text.toString(),fecha.text.toString())
            }else{
                password.error="Las contraseñas deben ser iguales"
                return null
            }
        }else{
            if( emailInput.text.isEmpty()) emailInput.error="Introduce un email válido"
            if (nombreUsuario.text.isEmpty()) nombreUsuario.error="Introduzca un nombre de usuario"
            if (password.text.isEmpty()) password.error="Introduzca una contraseña válida"
            if (passwordRepeat.text.isEmpty())passwordRepeat.error="Introduzca una contraseña válida"
            if (fecha.text.isEmpty()) fecha.error="Introduce una fecha válida"
            return null
        }

    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select image from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> chooseImageFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveImage(bitmap)
                    Toast.makeText(this, "Image Show!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)
                }
                catch (e: IOException)
                {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this, "Photo Show!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
        val wallpaperDirectory = File (
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }
        try
        {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".png"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.path), arrayOf("image/png"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException){
            e1.printStackTrace()
        }
        return ""
    }

    companion object {
        private const val IMAGE_DIRECTORY = "/nalhdaf"
    }
}
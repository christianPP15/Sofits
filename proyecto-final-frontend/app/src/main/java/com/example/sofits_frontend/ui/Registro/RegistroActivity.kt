package com.example.sofits_frontend.ui.Registro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.util.URIPathHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class RegistroActivity : AppCompatActivity() {
    private var button: Button? = null
    val REQUEST_CODE = 100
    var filePath : String? = null
    var selectedImage :Uri? = null
    @Inject lateinit var registerViewModel: RegistroViewModel
    val db = Firebase.firestore
    val uriPathHelper = URIPathHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_registro)

        var choose : Button = findViewById(R.id.button_choose_Imagen)
        choose.setOnClickListener {
            openGalleryForImage()
        }
        val botonRegistro= findViewById<Button>(R.id.buton_register)
        botonRegistro.setOnClickListener {
            val registerData:RegisterRequest?= sendRegisterInfo()
            if(registerData!=null){
                var registerDataResponse: RegisterResponse?
                if (filePath!=null && selectedImage!=null){
                    var file: File = File(filePath)
                    val requestFile = RequestBody.create(
                        MediaType.parse(this?.contentResolver.getType(selectedImage!!)),
                        file
                    )

                    registerViewModel.doRegisterComplete( MultipartBody.Part.createFormData("file","file",requestFile),registerData)
                }else{
                    findViewById<TextView>(R.id.textView_error_register).visibility= View.VISIBLE
                }

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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            filePath= uriPathHelper.getPath(this, data?.data!!).toString()
            selectedImage = data?.data
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,REQUEST_CODE)
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
}
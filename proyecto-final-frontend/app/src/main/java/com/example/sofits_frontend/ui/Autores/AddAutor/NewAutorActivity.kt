package com.example.sofits_frontend.ui.Autores.AddAutor

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.sofits_frontend.Api.request.AutorAddRequest
import com.example.sofits_frontend.Api.response.AutoresResponse.AddAutor.AddAutorResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.util.URIPathHelper
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class NewAutorActivity : AppCompatActivity() {
    lateinit var nombreAutor : TextView
    @Inject lateinit var addAutorViewModel: AddAutorViewModel
    lateinit var bio: TextView
    lateinit var fecha :TextView
    val REQUEST_CODE = 100
    var filePath : String? = null
    var selectedImage : Uri? = null
    lateinit var botonAddImagen :Button
    lateinit var imagenSubida : ImageView
    val uriPathHelper = URIPathHelper()
    lateinit var botonConfirmar : Button
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_autor)
        (this.applicationContext as MyApp).appComponent.inject(this)
        nombreAutor= findViewById(R.id.editText_nombreAutor)
        bio = findViewById(R.id.editTextTextMultiLine_biografia)
        fecha = findViewById(R.id.editTextDate_autor)
        botonAddImagen = findViewById(R.id.button_imagen_autor)
        imagenSubida = findViewById(R.id.imageView_autorAdd)
        botonConfirmar = findViewById(R.id.button_confirmar)
        botonAddImagen.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
            openGalleryForImage()
        }
        botonConfirmar.setOnClickListener {
            val autor = getAutorData()
            if (autor!=null){
                if (filePath!=null && selectedImage!=null){
                    var file: File = File(filePath)
                    val requestFile = RequestBody.create(
                        MediaType.parse(this?.contentResolver.getType(selectedImage!!)),
                        file
                    )
                    val jsonString = Gson().toJson(autor)
                    val resquestBodyData = RequestBody.create(MediaType.parse("application/json"), jsonString)
                    val multipar= MultipartBody.Part.createFormData("file",file.name,requestFile)
                    addAutorViewModel.addAutor(multipar ,resquestBodyData)
                    var navigation = Intent(this,MainActivity::class.java)
                    startActivity(navigation)
                }else{
                    val builder: AlertDialog.Builder? = this?.let {
                        AlertDialog.Builder(it)
                    }
                    builder?.setMessage("Seleccione una imagen para su publicación")
                        ?.setTitle("Imagen necesaria")
                    val dialog: AlertDialog? = builder?.create()
                    dialog?.show()
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imagenSubida.setImageURI(data?.data!!)
            filePath= uriPathHelper.getPath(this, data?.data!!).toString()
            selectedImage = data?.data
            imagenSubida.visibility= View.VISIBLE
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,REQUEST_CODE)
    }

    fun getAutorData(): AutorAddRequest? {
        if (nombreAutor.text.isEmpty() || bio.text.isEmpty() || fecha.text.isEmpty()){
            if (nombreAutor.text.isEmpty()) nombreAutor.error="Introduzca el nombre del autor"
            if (bio.text.isEmpty()) bio.error="Introduzca información sobre el autor"
            if (fecha.text.isEmpty()) fecha.error="Introduzca una fecha de nacimiento"
            return null
        }else{
            return AutorAddRequest(nombreAutor.text.toString(),bio.text.toString(),fecha.text.toString())
        }
    }
}
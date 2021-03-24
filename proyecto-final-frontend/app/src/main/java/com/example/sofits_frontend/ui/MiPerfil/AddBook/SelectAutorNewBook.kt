package com.example.sofits_frontend.ui.MiPerfil.AddBook

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.EditBook
import com.example.sofits_frontend.Api.response.AutoresResponse.Autor
import com.example.sofits_frontend.Api.response.AutoresResponse.Libro
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


class SelectAutorNewBook : AppCompatActivity() {
    @Inject lateinit var addBookViewModel: AddBookViewModel
    lateinit var spinner:Spinner
    val REQUEST_CODE = 100
    var filePath : String? = null
    var selectedImage : Uri? = null
    val uriPathHelper = URIPathHelper()
    lateinit var autores:List<Autor>
    lateinit var spineerLibros: Spinner
    lateinit var introChooseLibro: TextView
    lateinit var estadoLibro:TextView
    lateinit var edicion:TextView
    lateinit var descripcion : TextView
    lateinit var idioma : TextView
    lateinit var botonCompletar: Button
    lateinit var botonImagenes : Button
    lateinit var imagenSubida : ImageView
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_select_autor_new_book)

        botonImagenes=findViewById(R.id.buttonSubidaImagenesLibros)
        val idLibro= intent.extras?.getString("IdLibroEditar","")
        val estadoEditar = intent.extras?.getString("estado","")
        val idiomaEditar = intent.extras?.getString("idioma","")
        val descripcionEditar = intent.extras?.getString("descripcion","")
        val edicionEditar = intent.extras?.getInt("edicion",0)
        spinner=findViewById(R.id.spinner_autor)
        spineerLibros= findViewById<Spinner>(R.id.spinner_libros)
        introChooseLibro= findViewById(R.id.chooseLibro)
        estadoLibro= findViewById(R.id.input_estado)
        idioma = findViewById(R.id.input_idioma)
        val autor= findViewById<TextView>(R.id.chooseAutor)
        edicion = findViewById(R.id.editTextNumber_edion)
        descripcion = findViewById(R.id.input_descripcion)
        botonCompletar=findViewById(R.id.button_add_book)
        imagenSubida=findViewById(R.id.imageView_imagenSubida_libro)
        addBookViewModel.cargarAutores()
        if (idLibro!=null){
            idioma.visibility= View.VISIBLE
            estadoLibro.visibility= View.VISIBLE
            edicion.visibility= View.VISIBLE
            autor.visibility=View.INVISIBLE
            descripcion.visibility= View.VISIBLE
            botonCompletar.visibility = View.VISIBLE
            spineerLibros.visibility= View.INVISIBLE
            spinner.visibility=View.INVISIBLE
            introChooseLibro.visibility= View.INVISIBLE
            descripcion.text=descripcionEditar
            estadoLibro.text=estadoEditar
            botonImagenes.visibility=View.INVISIBLE
            imagenSubida.visibility=View.INVISIBLE
            edicion.text=edicionEditar.toString()
            idioma.text=idiomaEditar
            botonCompletar.setOnClickListener {
                val libroEditar= EditBook(descripcion.text.toString(),estadoLibro.text.toString(),idioma.text.toString(),edicion.text.toString())
                addBookViewModel.editarLibro(idLibro,libroEditar)
                val navegar = Intent(this,MainActivity::class.java)
                startActivity(navegar)
            }
        }else{
            addBookViewModel.MyInfoData.observe(this, Observer {result ->
                when(result){
                    is Resource.Success->{
                        autores=result.data!!.content
                        val listaNombres= mutableListOf("Escoja un autor")
                        for (nombre in result.data!!.content.map { it.nombre })
                            listaNombres.add(nombre)
                        spinner(listaNombres)
                    }
                }
            })
        }
        spineerLibros.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                null
            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                if (position!=0){
                    idioma.visibility= View.VISIBLE
                    estadoLibro.visibility= View.VISIBLE
                    edicion.visibility= View.VISIBLE
                    descripcion.visibility= View.VISIBLE
                    botonCompletar.visibility = View.VISIBLE
                    botonImagenes.visibility= View.VISIBLE
                    imagenSubida.visibility=View.VISIBLE
                    botonImagenes.setOnClickListener {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                        }
                        openGalleryForImage()
                    }
                    botonCompletar.setOnClickListener {
                        if (filePath!=null && selectedImage!=null){
                            var file = File(filePath)
                            val requestFile = RequestBody.create(
                                MediaType.parse(this?.contentResolver.getType(selectedImage!!)),
                                file
                            )
                            //val jsonString = Gson().toJson()
                            //val resquestBodyData = RequestBody.create(MediaType.parse("application/json"), jsonString)
                            val multipar= MultipartBody.Part.createFormData("file",file.name,requestFile)

                        }else{

                        }
                    }
                }else{
                    idioma.visibility= View.INVISIBLE
                    estadoLibro.visibility= View.INVISIBLE
                    edicion.visibility= View.INVISIBLE
                    descripcion.visibility= View.INVISIBLE
                    botonCompletar.visibility = View.INVISIBLE
                }
            }
        }
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                null
            }

            override fun onItemSelected(parentView: AdapterView<*>?,selectedItemView: View?,position: Int,id: Long) {
                if (position!=0){
                    val libros= mutableListOf("Escoja un libro")
                    for(titulo in autores.get(position-1).libros.map { it.titulo })
                        libros.add(titulo)
                    spinnerLibro(libros)
                    spineerLibros.visibility= View.VISIBLE
                    introChooseLibro.visibility= View.VISIBLE
                }else{
                    spineerLibros.visibility= View.INVISIBLE
                    introChooseLibro.visibility= View.INVISIBLE
                }
            }
        }

    }
    private fun spinner(lista:List<String>){
        ArrayAdapter(this,android.R.layout.simple_spinner_item,lista).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
    private fun spinnerLibro(lista:List<String>){
        ArrayAdapter(this, android.R.layout.simple_spinner_item,lista).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spineerLibros.adapter=adapter
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imagenSubida.setImageURI(data?.data!!)
            filePath= uriPathHelper.getPath(this, data?.data!!).toString()
            selectedImage = data?.data
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,REQUEST_CODE)
    }
}
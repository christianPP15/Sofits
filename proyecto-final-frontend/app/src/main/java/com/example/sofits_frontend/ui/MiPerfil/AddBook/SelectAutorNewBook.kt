package com.example.sofits_frontend.ui.MiPerfil.AddBook

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.Autor
import com.example.sofits_frontend.Api.response.AutoresResponse.Libro
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject


class SelectAutorNewBook : AppCompatActivity() {
    @Inject lateinit var addBookViewModel: AddBookViewModel
    lateinit var spinner:Spinner
    lateinit var autores:List<Autor>
    lateinit var spineerLibros: Spinner
    lateinit var introChooseLibro: TextView
    lateinit var estadoLibro:TextView
    lateinit var edicion:TextView
    lateinit var descripcion : TextView
    lateinit var idioma : TextView
    lateinit var botonCompletar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_select_autor_new_book)
        spinner=findViewById(R.id.spinner_autor)
        spineerLibros= findViewById<Spinner>(R.id.spinner_libros)
        introChooseLibro= findViewById(R.id.chooseLibro)
        estadoLibro= findViewById(R.id.input_estado)
        idioma = findViewById(R.id.input_idioma)
        edicion = findViewById(R.id.editTextNumber_edion)
        descripcion = findViewById(R.id.input_descripcion)
        botonCompletar=findViewById(R.id.button_add_book)
        addBookViewModel.cargarAutores()
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
}
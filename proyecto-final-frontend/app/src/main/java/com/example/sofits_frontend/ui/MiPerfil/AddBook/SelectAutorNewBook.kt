package com.example.sofits_frontend.ui.MiPerfil.AddBook

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_select_autor_new_book)
        spinner=findViewById(R.id.spinner_autor)
        addBookViewModel.cargarAutores()
        addBookViewModel.MyInfoData.observe(this, Observer {result ->
            when(result){
                is Resource.Success->{
                    autores=result.data!!.content
                    val listaNombres= mutableListOf<String>("Escoja un autor")
                    for (nombre in result.data!!.content.map { it.nombre })
                        listaNombres.add(nombre)
                    spinner(listaNombres)
                }
            }
        })

        /*spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?,selectedItemView: View?,position: Int,id: Long) {
                val libros=autores.get(position).libros.map { it.titulo }
                //val adapter: ArrayAdapter<String> = ArrayAdapter(applicationContext,R.layout.activity_select_autor_new_book,R.id.chooseLibro,libros)
                //val spineerLibros= findViewById<Spinner>(R.id.spinnerLibro)
                //spineerLibros.adapter=adapter
                //spineerLibros.visibility= View.VISIBLE
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }*/

    }
    private fun spinner(lista:List<String>){
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            lista
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}
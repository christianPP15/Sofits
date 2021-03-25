package com.example.sofits_frontend.ui.Libros

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.PublicacionesResponse.PublicacionesResponse
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject
import javax.inject.Named

class PublicacionesActivity : AppCompatActivity() {
    @Inject
    @Named("providePublicacionesViewModel") lateinit var publicadosViewModel: LibrosPublicadosViewModel
    lateinit var result : PublicacionesResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_publicaciones)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        val id = intent.extras?.getString("idLibro")
        publicadosViewModel.getPublicaciones(id!!)
        val generos = findViewById<TextView>(R.id.textView_generos_publicaciones)
        val descripcion = findViewById<TextView>(R.id.text_view_descripcion_publicacion)
        val botonFab= findViewById<FloatingActionButton>(R.id.fab)
        publicadosViewModel.publicacionesData.observe(this, Observer {response->
            when (response) {
                is Resource.Success -> {
                    result=response.data!!
                    findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = response.data!!.libro.titulo
                    generos.text=response.data!!.libro.generos
                    descripcion.text=response.data!!.libro.descripcion
                    if (result.libro.meGustaUsuario){
                        botonFab.setImageResource(R.drawable.ic_corazon)
                    }
                }
            }
        })
        botonFab.setOnClickListener { view ->
            if (result.libro.meGustaUsuario){
                publicadosViewModel.removeMeGustaLibro(id)
                botonFab.setImageResource(R.drawable.ic_favorito_vacio)
                result.libro.meGustaUsuario=false
            }else{
                publicadosViewModel.addMeGustaLibro(id)
                botonFab.setImageResource(R.drawable.ic_corazon)
                result.libro.meGustaUsuario=true
            }
        }
    }
}
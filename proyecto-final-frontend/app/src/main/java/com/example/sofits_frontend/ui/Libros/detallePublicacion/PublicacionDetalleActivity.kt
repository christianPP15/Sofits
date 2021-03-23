package com.example.sofits_frontend.ui.Libros.detallePublicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import coil.load
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.google.android.material.appbar.CollapsingToolbarLayout
import javax.inject.Inject

class PublicacionDetalleActivity : AppCompatActivity() {
    @Inject lateinit var publicacionDetalleViewModel: PublicacionDetalleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
        (this.applicationContext as MyApp).appComponent.inject(this)
        val idLibro = intent.extras?.getString("idLibro")
        val idUsuario = intent.extras?.getString("idUsuario")
        publicacionDetalleViewModel.getPublicaciones(idLibro!!, idUsuario!!)
        val imagen: ImageView = findViewById(R.id.imageView_del_libro_detalle_publicacion)
        val titulo: TextView = findViewById(R.id.textView_titulo_libro)
        val estado : TextView = findViewById(R.id.textView_estado_libro_detalle)
        val idioma: TextView = findViewById(R.id.textView_idioma_detail_publicacion)
        val edicion : TextView = findViewById(R.id.textView_edicion_detail_publicacion)
        val descripcion : TextView = findViewById(R.id.textView_descripcion_detail_publicacion)
        val botonChat : Button = findViewById(R.id.button_start_chat)
        publicacionDetalleViewModel.publicacionData.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    val result = response.data!!
                    if (response.data!!.imagen==null){
                        imagen.load("https://eu.ui-avatars.com/api/?name="+result.libro.titulo)
                    }
                    titulo.text=result.libro.titulo
                    estado.text=result.estado
                    idioma.text=result.idioma
                    edicion.text=result.edicion.toString()+"º Edición"
                    descripcion.text=result.descripcion
                    title=result.usuario.nombre
                }
            }
        })
    }
}
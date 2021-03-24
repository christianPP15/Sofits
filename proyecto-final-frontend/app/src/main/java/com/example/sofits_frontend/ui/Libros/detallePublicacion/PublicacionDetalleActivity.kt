package com.example.sofits_frontend.ui.Libros.detallePublicacion

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import coil.load
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.PublicacionesResponse.detalles.DetallePublicacionResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.MiPerfil.AddBook.SelectAutorNewBook
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class PublicacionDetalleActivity : AppCompatActivity() {
    @Inject lateinit var publicacionDetalleViewModel: PublicacionDetalleViewModel
    lateinit var result: DetallePublicacionResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
        (this.applicationContext as MyApp).appComponent.inject(this)

        val sharedPref =this?.getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
        val imagen: ImageView = findViewById(R.id.imageView_del_libro_detalle_publicacion)
        val titulo: TextView = findViewById(R.id.textView_titulo_libro)
        val intercambiado : TextView = findViewById(R.id.textView_intercambiado)
        val estado : TextView = findViewById(R.id.textView_estado_libro_detalle)
        val idioma: TextView = findViewById(R.id.textView_idioma_detail_publicacion)
        val edicion : TextView = findViewById(R.id.textView_edicion_detail_publicacion)
        val descripcion : TextView = findViewById(R.id.textView_descripcion_detail_publicacion)
        val botonChat : Button = findViewById(R.id.button_start_chat)
        val idLibro = intent.extras?.getString("idLibro")
        val idUsuario = intent.extras?.getString("idUsuario")
        val idLibroPropio = intent.extras?.getString("idMiLibro")
        val idUsuarioMismo = sharedPref?.getString(getString(R.string.IdentificadorUsuario),"")
        if (idLibro!=null && idUsuario!=null){
            publicacionDetalleViewModel.getPublicaciones(idLibro!!, idUsuario!!)
            publicacionDetalleViewModel.publicacionData.observe(this, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        result = response.data!!
                        if (response.data!!.imagen==null){
                            imagen.load("https://eu.ui-avatars.com/api/?name="+result.libro.titulo)
                        }else{
                            imagen.load(Constantes.imageURL+response.data.imagen!!.idImagen+".png")
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
        }else if (idLibroPropio!=null && idUsuarioMismo!=null){
            botonChat.text="Editar/Eliminar"
            val botonCambiar :FloatingActionButton = findViewById(R.id.floatingActionButton_intercambiar)
            botonCambiar.visibility= View.VISIBLE
            publicacionDetalleViewModel.getPublicaciones(idLibroPropio!!, idUsuarioMismo!!)
            botonChat.setOnClickListener {
                val alertDialog: AlertDialog? = this?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setTitle(R.string.EliminarEditarTitle)
                        setMessage(R.string.EliminarEditarPregunta)
                        setPositiveButton(R.string.Editar,
                            DialogInterface.OnClickListener { dialog, id ->
                                val navegation = Intent(it, SelectAutorNewBook::class.java).apply {
                                    putExtra("IdLibroEditar",result.id.libro_id)
                                    putExtra("estado",result.estado)
                                    putExtra("idioma",result.idioma)
                                    putExtra("descripcion",result.descripcion)
                                    putExtra("edicion",result.edicion)
                                }
                                startActivity(navegation)
                            })
                        setNegativeButton(R.string.Eliminar,
                            DialogInterface.OnClickListener { dialog, id ->
                                publicacionDetalleViewModel.eliminarPublicacion(idLibroPropio)
                                val navigation = Intent(it,MainActivity::class.java)
                                startActivity(navigation)
                            })
                    }
                    builder.create()
                }
                alertDialog?.show()
            }
            botonCambiar.setOnClickListener {
                publicacionDetalleViewModel.changeState(idLibroPropio)
                result.intercambiado=!result.intercambiado
                intercambiado.text= if (result.intercambiado) "Este libro ya ha sido intercambiado" else "Este libro aún no ha sido intercambiado"
            }
            publicacionDetalleViewModel.publicacionData.observe(this, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        result= response.data!!
                        if (response.data!!.imagen==null){
                            imagen.load("https://eu.ui-avatars.com/api/?name="+result.libro.titulo)
                        }else{
                            imagen.load(Constantes.imageURL+result.imagen!!.idImagen+".png")
                        }
                        titulo.text=result.libro.titulo
                        estado.text=result.estado
                        idioma.text=result.idioma
                        edicion.text=result.edicion.toString()+"º Edición"
                        descripcion.text=result.descripcion
                        title=result.usuario.nombre
                        intercambiado.visibility=View.VISIBLE
                        intercambiado.text= if (result.intercambiado) "Este libro ya ha sido intercambiado" else "Este libro aún no ha sido intercambiado"
                    }
                }
            })
        }else{
            val navigation = Intent(this, MainActivity::class.java)
            startActivity(navigation)
        }

    }
}
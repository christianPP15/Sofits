package com.example.sofits_frontend.ui.MiPerfil.Informacion

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject
import kotlin.math.roundToInt


class InformacionUsuarioFragment : Fragment() {

    @Inject
    lateinit var informacionUsuarioViewModel: InformacionUsuarioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        informacionUsuarioViewModel.infoData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    val imagenUsuario =
                        view?.findViewById<ImageView>(R.id.imageView_perfilUsuarioImagen)
                    val textViewNombreUsuario =
                        view?.findViewById<TextView>(R.id.textView_nombreUsuario)
                    val usuario = response.data!!.usuarioValorado
                    comprobarValoracion(response.data!!.media)
                    textViewNombreUsuario?.text = usuario.nombre
                    if (usuario.imagen != null) {
                        imagenUsuario!!.load(Constantes.imageURL + usuario.imagen.idImagen+".png"){
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    } else {
                        imagenUsuario!!.load("https://eu.ui-avatars.com/api/?size=300&name=" + usuario.nombre){
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
            }
        })
        return inflater.inflate(R.layout.fragment_informacion_usuario, container, false)
    }

    fun comprobarValoracion(valoracion: Double) {
        val estrella1 = view?.findViewById<ImageView>(R.id.imageView_estrella1_valoracionPropia)
        val estrella2 = view?.findViewById<ImageView>(R.id.imageView_estrella2_valoracionPropia)
        val estrella3 = view?.findViewById<ImageView>(R.id.imageView_estrella3_valoracionPropia)
        val estrella4 = view?.findViewById<ImageView>(R.id.imageView_estrella4_valoracionPropia)
        val estrella5 = view?.findViewById<ImageView>(R.id.imageView_estrella5_valoracionPropia)
        when (valoracion.roundToInt()) {
            1 -> {
                estrella1?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella2?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella3?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella4?.setImageResource(R.drawable.ic_estrella_rellena)
            }
            2 -> {
                estrella1?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella2?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella3?.setImageResource(R.drawable.ic_estrella_rellena)
            }
            3 -> {
                estrella1?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella2?.setImageResource(R.drawable.ic_estrella_rellena)
            }
            4 -> {
                estrella1?.setImageResource(R.drawable.ic_estrella_rellena)
            }
            0 -> {
                estrella1?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella2?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella3?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella4?.setImageResource(R.drawable.ic_estrella_rellena)
                estrella5?.setImageResource(R.drawable.ic_estrella_rellena)
            }
            else -> null
        }

    }

}
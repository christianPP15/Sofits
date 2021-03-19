package com.example.sofits_frontend.ui.MiPerfil.Informacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import coil.load
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject


class InformacionUsuarioFragment : Fragment() {

    @Inject lateinit var informacionUsuarioViewModel: InformacionUsuarioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        informacionUsuarioViewModel.infoData.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    val imagenUsuario = view?.findViewById<ImageView>(R.id.imageView_perfilUsuarioImagen)
                    val usuario = response.data!!.usuarioValorado
                    if (usuario.imagen!=null){
                        imagenUsuario!!.load(Constantes.imageURL+usuario.imagen.idImagen)
                    }else{
                        imagenUsuario!!.load("https://eu.ui-avatars.com/api/?size=300?name="+usuario.nombre)
                    }
                }
            }
        })
        return inflater.inflate(R.layout.fragment_informacion_usuario, container, false)
    }

}
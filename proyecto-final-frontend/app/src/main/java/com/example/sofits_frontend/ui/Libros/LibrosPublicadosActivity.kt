package com.example.sofits_frontend.ui.Libros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject
import javax.inject.Named

class LibrosPublicadosActivity : AppCompatActivity() {
    @Inject @Named("providePublicacionesViewModel") lateinit var publicadosViewModel: LibrosPublicadosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_libros_publicados)
        val id = intent.extras?.getString("idLibro")
        publicadosViewModel.getPublicaciones(id!!)
        publicadosViewModel.publicacionesData.observe(this, Observer {response->
            when (response) {
                is Resource.Success -> {
                    title=response.data!!.libro.titulo
                }
            }
        })
    }
}
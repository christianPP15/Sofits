package com.example.sofits_frontend.ui.Autores.AutoresDetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.AutorDetailResponse
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.common.MyApp
import retrofit2.Response
import javax.inject.Inject

class AutorDetailActivity : AppCompatActivity() {

    @Inject lateinit var autoresDetailsViewModel: AutoresDetailsViewModel
    var result:AutorDetailResponse?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor_detail)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setSupportActionBar(findViewById(R.id.toolbar))
        val id= intent.extras?.getString("idAutor")
        autoresDetailsViewModel.getAutorInfo(id!!)

        autoresDetailsViewModel.AutoresData.observe(this, Observer {response->
            when (response){
                is Resource.Success->{
                 result=response.data!!
                    findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = result!!.nombre
                    if (result?.imagen!=null){
                        findViewById<ImageView>(R.id.toolbarImage).load(Constantes.imageURL+result!!.imagen!!.idImagen){
                            crossfade(true)
                        }
                    }else{
                        findViewById<ImageView>(R.id.toolbarImage).load("https://eu.ui-avatars.com/api/?name="+result!!.nombre){
                            crossfade(true)
                        }
                    }
                    findViewById<TextView>(R.id.textView_Nombre_anio).text=result!!.nombre+ "\t"+result!!.nacimiento
                    findViewById<TextView>(R.id.text_view_descripcion).text=result!!.biografia
                }
            }
        })

        val meGusta = findViewById<FloatingActionButton>(R.id.fab)
        meGusta.setOnClickListener { view ->
            if (result!=null){
                if (!result!!.meGusta){
                    autoresDetailsViewModel.MeGustaAutor(id!!)
                    meGusta.setImageResource(R.drawable.ic_corazon)
                    result!!.meGusta=true
                }else{
                    autoresDetailsViewModel.RemoveMeGusta(id!!)
                    meGusta.setImageResource(R.drawable.ic_favorito_vacio)
                    result!!.meGusta=false
                }
            }
        }
    }
}
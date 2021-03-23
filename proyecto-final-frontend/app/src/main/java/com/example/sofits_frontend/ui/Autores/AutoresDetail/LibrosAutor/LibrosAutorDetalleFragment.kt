package com.example.sofits_frontend.ui.Autores.AutoresDetail.LibrosAutor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.Libro
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.Autores.AutoresDetail.AutoresDetailsViewModel
import javax.inject.Inject
import javax.inject.Named


class LibrosAutorDetalleFragment: Fragment() {

    lateinit var miLibroDetalleRecyclerView : MyLibrosAutorDetalleRecyclerViewAdapter
    @Inject @Named("provideAutoresDetailsViewModel") lateinit var autoresDetailsViewModel: AutoresDetailsViewModel
    private var columnCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_libro_autor_detalle_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.listview)
        miLibroDetalleRecyclerView= MyLibrosAutorDetalleRecyclerViewAdapter(context as Context)
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = miLibroDetalleRecyclerView
        }

        autoresDetailsViewModel.AutoresData.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.Success -> {
                    setData(response.data!!.libros)
                }
            }
        })


        return view
    }

    fun setData(list:List<Libro>){
        miLibroDetalleRecyclerView.setData(list)
    }
}
package com.example.sofits_frontend.ui.Libros

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import javax.inject.Inject
import javax.inject.Named


class PublicacionFragment @Inject constructor() : Fragment() {
    @Inject @Named("providePublicacionesViewModel") lateinit var publicadosViewModel: LibrosPublicadosViewModel
    private var columnCount = 1
    @Inject lateinit var publicacionRecyclerViewAdapter: MyPublicacionRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_publicacion_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        with(recyclerView) {
            layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            adapter = publicacionRecyclerViewAdapter
        }
        publicadosViewModel.publicacionesData.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.Success -> {
                    publicacionRecyclerViewAdapter.setData(response.data!!.publicaciones)
                }
            }
        })
        return view
    }
}
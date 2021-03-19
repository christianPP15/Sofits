package com.example.sofits_frontend.ui.Autores

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.MiPerfil.MyMisLibrosRecyclerViewAdapter
import javax.inject.Inject

class AutoresFragment : Fragment() {

    @Inject lateinit var autoresViewModel: AutoresViewModel
    @Inject lateinit var miAutoresRecyclerViewAdapter: MyAutoresRecyclerViewAdapter
    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_autor_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = miAutoresRecyclerViewAdapter
            }
        autoresViewModel.AutoresData.observe(viewLifecycleOwner, Observer {response->
            when(response) {
                is Resource.Success->{
                    miAutoresRecyclerViewAdapter.setData(response.data?.content!!)
                    recyclerView.scheduleLayoutAnimation()
                }
                is Resource.Error -> {
                    Toast.makeText(activity, "Error: ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
        return view
    }

}
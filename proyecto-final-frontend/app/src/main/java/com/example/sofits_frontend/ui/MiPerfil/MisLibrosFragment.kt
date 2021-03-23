package com.example.sofits_frontend.ui.MiPerfil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.MiPerfil.AddBook.SelectAutorNewBook
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MisLibrosFragment : Fragment() {
    @Inject lateinit var misLibrosViewModel: MisLibrosViewModel
    private var columnCount = 2
    lateinit var misLibrosRecyclerViewAdapter: MyMisLibrosRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mi_libro_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        misLibrosRecyclerViewAdapter= MyMisLibrosRecyclerViewAdapter(context as Context)

        with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
         }
            adapter=misLibrosRecyclerViewAdapter
        }
        val newBook= view?.findViewById<FloatingActionButton>(R.id.fab_add_book)
        newBook?.setOnClickListener {
            var navigation = Intent(context, SelectAutorNewBook::class.java)
            startActivity(navigation)
        }
        misLibrosViewModel.MyInfoData.observe(viewLifecycleOwner, Observer {response->
            when(response) {
                is Resource.Success->{
                    misLibrosRecyclerViewAdapter.setData(response.data?.content!!)
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
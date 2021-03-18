package com.example.sofits_frontend.ui.MiPerfil

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
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.R

class MisLibrosFragment : Fragment() {

    private var columnCount = 2
    private lateinit var misLibrosRecyclerViewAdapter: MyMisLibrosRecyclerViewAdapter
    private lateinit var misLibrosViewModel: MisLibrosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        misLibrosViewModel=ViewModelProvider(this).get(MisLibrosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mi_libro_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        misLibrosRecyclerViewAdapter=MyMisLibrosRecyclerViewAdapter()

        with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
         }
            adapter=misLibrosRecyclerViewAdapter
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
package com.example.sofits_frontend.ui.Autores

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sofits_frontend.Api.response.AutoresResponse.Autor
import com.example.sofits_frontend.R

class MyAutoresRecyclerViewAdapter() : RecyclerView.Adapter<MyAutoresRecyclerViewAdapter.ViewHolder>() {
    var values: List<Autor> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_autor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        //holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
    }
    fun setData(lista:List<Autor>){
        values=lista
        notifyDataSetChanged()
    }
}
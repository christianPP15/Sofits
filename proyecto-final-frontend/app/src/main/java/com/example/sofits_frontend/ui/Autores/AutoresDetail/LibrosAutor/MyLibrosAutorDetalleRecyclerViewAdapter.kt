package com.example.sofits_frontend.ui.Autores.AutoresDetail.LibrosAutor

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.Libro
import com.example.sofits_frontend.R
import javax.inject.Inject


class MyLibrosAutorDetalleRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<MyLibrosAutorDetalleRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Libro> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_libro_autor_detalle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titulo.text=item.titulo
        holder.unidades.text=item.unidades.toString()+" Ud."
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textView_titulo)
        val unidades: TextView = view.findViewById(R.id.textView_unidades)

    }
    fun setData(libros:List<Libro>){
        values=libros
    }
}
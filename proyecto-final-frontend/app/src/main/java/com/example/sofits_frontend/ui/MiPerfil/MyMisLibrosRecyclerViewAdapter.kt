package com.example.sofits_frontend.ui.MiPerfil

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.LibrosSubidos
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import javax.inject.Inject


class MyMisLibrosRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<MyMisLibrosRecyclerViewAdapter.ViewHolder>() {
    private var values: List<LibrosSubidos> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_mi_libro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.imagenWithoutHash!=null){
            holder.imagenLibroUsuario.load(Constantes.imageURL+item.imagenWithoutHash.idImagen)
        }
        holder.nombreLibro.text=item.libro.titulo
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenLibroUsuario: ImageView = view.findViewById(R.id.imageView_imagen_libro_my_info)
        val nombreLibro: TextView = view.findViewById(R.id.textView_nombreLibro_my_info)
    }

    fun setData(list:List<LibrosSubidos>){
        values=list
        notifyDataSetChanged()
    }
}
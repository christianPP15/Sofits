package com.example.sofits_frontend.ui.Libros

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.sofits_frontend.Api.response.PublicacionesResponse.LibrosUsuariosResponse
import com.example.sofits_frontend.R
import javax.inject.Inject


class MyPublicacionRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<MyPublicacionRecyclerViewAdapter.ViewHolder>() {
    private var values: List<LibrosUsuariosResponse> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_publicacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.imagen==null){
            holder.imageView.load("https://eu.ui-avatars.com/api/?size=300&name="+item.usuario.nombre)
        }
        holder.nombreUsuario.text=item.usuario.nombre
        holder.idioma.text=item.idioma
        holder.estado.text=item.estado
        holder.edicion.text=item.edicion.toString()+" Edición"

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView= view.findViewById(R.id.imageView_imagenUsuario)
        val nombreUsuario: TextView = view.findViewById(R.id.textView_nombreCliente)
        val idioma :TextView = view.findViewById(R.id.textView_idioma)
        val estado: TextView = view.findViewById(R.id.textView_estado)
        val edicion : TextView = view.findViewById(R.id.textView_edicion)
    }
    fun setData(lista:List<LibrosUsuariosResponse>){
        values=lista
    }
}
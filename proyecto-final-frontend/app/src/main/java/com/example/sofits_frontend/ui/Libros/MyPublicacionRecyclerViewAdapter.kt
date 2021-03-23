package com.example.sofits_frontend.ui.Libros

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.sofits_frontend.Api.response.PublicacionesResponse.LibrosUsuariosResponse
import com.example.sofits_frontend.R
import com.example.sofits_frontend.ui.Autores.AutoresDetail.AutorDetailActivity
import com.example.sofits_frontend.ui.Libros.detallePublicacion.PublicacionDetalleActivity
import javax.inject.Inject


class MyPublicacionRecyclerViewAdapter constructor(val ctx:Context) : RecyclerView.Adapter<MyPublicacionRecyclerViewAdapter.ViewHolder>() {
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
        holder.edicion.text=item.edicion.toString()+"º Edición"
        holder.constraint.setOnClickListener {
            val navigation = Intent(ctx, PublicacionDetalleActivity::class.java).apply {
                putExtra("idLibro",item.id.libro_id)
                putExtra("idUsuario",item.id.usuario_id)
            }
            ctx.startActivity(navigation)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView= view.findViewById(R.id.imageView_imagenUsuario)
        val nombreUsuario: TextView = view.findViewById(R.id.textView_nombreCliente)
        val idioma :TextView = view.findViewById(R.id.textView_idioma)
        val estado: TextView = view.findViewById(R.id.textView_estado)
        val edicion : TextView = view.findViewById(R.id.textView_edicion)
        val constraint: ConstraintLayout = view.findViewById(R.id.constraint_publicacion)
    }
    fun setData(lista:List<LibrosUsuariosResponse>){
        values=lista
        notifyDataSetChanged()
    }
}
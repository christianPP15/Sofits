package com.example.sofits_frontend.ui.MiPerfil

import android.app.AlertDialog
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
import coil.transform.CircleCropTransformation
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.LibrosSubidos
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.ui.Libros.detallePublicacion.PublicacionDetalleActivity
import javax.inject.Inject


class MyMisLibrosRecyclerViewAdapter constructor(val ctx:Context) : RecyclerView.Adapter<MyMisLibrosRecyclerViewAdapter.ViewHolder>() {
    private var values: List<LibrosSubidos> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_mi_libro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.imagenWithoutHash!=null){
            holder.imagenLibroUsuario.load(Constantes.imageURL+item.imagenWithoutHash.idImagen+".png"){
                crossfade(true)
            }
        }else{

        }
        holder.nombreLibro.text=item.libro.titulo
        holder.contraint.setOnClickListener {
            val navigation = Intent(ctx,PublicacionDetalleActivity::class.java).apply {
                putExtra("idMiLibro",item.libro.id)
            }
            ctx.startActivity(navigation)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenLibroUsuario: ImageView = view.findViewById(R.id.imageView_imagen_libro_my_info)
        val nombreLibro: TextView = view.findViewById(R.id.textView_nombreLibro_my_info)
        val contraint: ConstraintLayout= view.findViewById(R.id.constraint_mi_libro)
    }

    fun setData(list:List<LibrosSubidos>){
        values=list
        notifyDataSetChanged()
    }
}
package com.example.sofits_frontend.ui.Autores

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
import com.example.sofits_frontend.Api.response.AutoresResponse.Autor
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.repository.SofitsRepository
import com.example.sofits_frontend.ui.Autores.AutoresDetail.AutorDetailActivity
import javax.inject.Inject

class MyAutoresRecyclerViewAdapter  constructor(val ctx:Context) : RecyclerView.Adapter<MyAutoresRecyclerViewAdapter.ViewHolder>() {
    var values: List<Autor> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_autor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.imagen!=null){
            holder.imagenAutor.load(Constantes.imageURL+item.imagen.idImagen)
        }
        holder.nombreAutor.text=item.nombre
        holder.autorConstraint.setOnClickListener {
            val navigation = Intent(ctx,AutorDetailActivity::class.java).apply {
                putExtra("idAutor",item.id)
            }
            ctx.startActivity(navigation)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenAutor: ImageView = view.findViewById(R.id.imageView_autor)
        val nombreAutor: TextView = view.findViewById(R.id.textView_nombre_autor)
        val autorConstraint= view.findViewById<ConstraintLayout>(R.id.autor_constraint_Main)
    }
    fun setData(lista:List<Autor>){
        values=lista
        notifyDataSetChanged()
    }
}
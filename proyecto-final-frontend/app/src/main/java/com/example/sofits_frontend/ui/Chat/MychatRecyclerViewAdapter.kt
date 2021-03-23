package com.example.sofits_frontend.ui.Chat

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.sofits_frontend.Api.response.ChatResponse.chatsResponse
import com.example.sofits_frontend.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import javax.inject.Inject


class MychatRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<MychatRecyclerViewAdapter.ViewHolder>() {
    private var values: List<chatsResponse> = ArrayList()
    val db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nombreUsuario.text="pepe"

        db.collection("users")
            .document(item.idUsuario2)
            .get()
            .addOnSuccessListener { result ->
                holder.nombreUsuario.text=result.data!!.get("nombre").toString()
                holder.UltimoMensaje.text=item.mensajes.last()
                holder.imagen.load("https://eu.ui-avatars.com/api/?size=150&name="+result.data!!.get("nombre"))
            }
            .addOnFailureListener { exception ->
                null
            }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreUsuario: TextView = view.findViewById(R.id.textView_NombreUsuario)
        val UltimoMensaje: TextView = view.findViewById(R.id.textView_ultimoMensaje)
        val imagen : ImageView = view.findViewById(R.id.imageView_chat_usuario)
    }
    fun setData(lista:MutableList<chatsResponse>){
        values=lista
        notifyDataSetChanged()
    }
}
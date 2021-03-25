package com.example.sofits_frontend.ui.Chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sofits_frontend.Api.response.ChatResponse.chatsResponse
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class chatFragment : Fragment() {

    private var columnCount = 1
    @Inject lateinit var mychatRecyclerViewAdapter: MychatRecyclerViewAdapter
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = mychatRecyclerViewAdapter
        }
        val shared = activity?.getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
        val result: MutableList<chatsResponse> = mutableListOf()
        db.collection("chats")
            .whereEqualTo(
                "idUsuario1",
                shared?.getString(getString(R.string.IdentificadorUsuario), "")
            )
            .get()
            .addOnSuccessListener { chats ->
                for (document in chats.documents) {
                    result.add(
                        chatsResponse(
                        document.data!!.get("idUsuario1") as String,
                            document.data!!.get("idUsuario2") as String,
                            document.data!!.get("mensajes") as List<String>
                    ))
                }
                mychatRecyclerViewAdapter.setData(result)
            }
            .addOnFailureListener { ex ->
                Log.i("RESULTADOS", ex.message!!)
            }

        return view
    }

}
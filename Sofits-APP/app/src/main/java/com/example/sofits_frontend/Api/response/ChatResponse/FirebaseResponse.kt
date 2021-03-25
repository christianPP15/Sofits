package com.example.sofits_frontend.Api.response.ChatResponse

data class chatsResponse(
    val idUsuario1:String,
    val idUsuario2:String,
    val mensajes: List<String>
)
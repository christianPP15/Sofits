package com.example.sofits_frontend.Api.response.PublicacionesResponse

data class Libro(
    val id: String,
    val titulo: String,
    val generos:String,
    val descripcion:String,
    var meGustaUsuario:Boolean
)
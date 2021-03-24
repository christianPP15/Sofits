package com.example.sofits_frontend.Api.response.AutoresResponse.AddAutor

data class AddAutorResponse(
    val biografia: String,
    val id: String,
    val imagen: Imagen,
    val nacimiento: String,
    val nombre: String
)
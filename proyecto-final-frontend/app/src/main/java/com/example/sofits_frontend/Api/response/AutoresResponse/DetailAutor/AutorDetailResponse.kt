package com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash

data class AutorDetailResponse(
    val biografia: String,
    val id: String,
    val libros: List<Libro>,
    val nacimiento: String,
    val imagen:ImagenWithoutHash?,
    val nombre: String,
    var meGusta:Boolean
)
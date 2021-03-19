package com.example.sofits_frontend.Api.response.AutoresResponse

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash

data class Autor(
    val id: String,
    val libros: List<Libro>,
    val nombre: String,
    val imagen: ImagenWithoutHash?
)
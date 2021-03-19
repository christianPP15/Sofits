package com.example.sofits_frontend.Api.response.AutoresResponse

data class Autor(
    val id: String,
    val libros: List<Libro>,
    val nombre: String
)
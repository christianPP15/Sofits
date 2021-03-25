package com.example.sofits_frontend.Api.response.PublicacionesResponse.meGustaLibro

data class AddMeGustaLibroResponse(
    val autor: Autor,
    val descripcion: String,
    val id: String,
    val titulo: String
)
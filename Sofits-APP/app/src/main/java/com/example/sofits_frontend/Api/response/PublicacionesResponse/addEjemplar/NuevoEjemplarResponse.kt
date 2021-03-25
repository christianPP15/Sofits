package com.example.sofits_frontend.Api.response.PublicacionesResponse.addEjemplar

data class NuevoEjemplarResponse(
    val descripcion: String,
    val edicion: Int,
    val estado: String,
    val id: Id,
    val idioma: String,
    val imagen: Imagen,
    val intercambiado: Boolean,
    val usuario: Usuario
)
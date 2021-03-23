package com.example.sofits_frontend.Api.response.PublicacionesResponse

data class PublicacionesResponse(
    val libro: Libro,
    val publicaciones: List<LibrosUsuariosResponse>
)
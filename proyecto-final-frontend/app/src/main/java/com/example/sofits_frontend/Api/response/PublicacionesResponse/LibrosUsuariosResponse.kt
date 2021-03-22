package com.example.sofits_frontend.Api.response.PublicacionesResponse

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash

data class LibrosUsuariosResponse(
    val descripcion: String,
    val edicion: Int,
    val estado: String,
    val idioma: String,
    val imagen: ImagenWithoutHash?,
    val intercambiado: Boolean,
    val usuario: Usuario
)
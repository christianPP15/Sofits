package com.example.sofits_frontend.Api.response.MiPerfilResponse

import java.util.*

data class LibrosSubidos(
    val descripcion: String,
    val edicion: Int,
    val estado: String,
    val imagenWithoutHash:ImagenWithoutHash ,
    val intercambiado: Boolean,
    val libro: Libro,
    val usuario: Usuario
)

data class ImagenWithoutHash(
    val id: UUID?,
    val idImagen:String?
)
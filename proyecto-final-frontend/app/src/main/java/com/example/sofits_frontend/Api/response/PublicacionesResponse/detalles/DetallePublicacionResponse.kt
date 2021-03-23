package com.example.sofits_frontend.Api.response.PublicacionesResponse.detalles

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash
import com.example.sofits_frontend.Api.response.PublicacionesResponse.idPublicacion

data class DetallePublicacionResponse(
    val descripcion: String,
    val edicion: Int,
    val estado: String,
    val id: idPublicacion,
    val idioma: String,
    val imagen: ImagenWithoutHash?,
    val intercambiado: Boolean,
    val usuario: Usuario,
    val libro: LibroDetail
)
package com.example.sofits_frontend.Api.response.PublicacionesResponse

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash

data class LibrosUsuariosResponse(
    val id : idPublicacion,
    val descripcion: String,
    val edicion: Int,
    val estado: String,
    val idioma: String,
    val imagen: ImagenWithoutHash?,
    val intercambiado: Boolean,
    val usuario: Usuario
)
data class idPublicacion(
    val usuario_id : String,
    val libro_id:String
)
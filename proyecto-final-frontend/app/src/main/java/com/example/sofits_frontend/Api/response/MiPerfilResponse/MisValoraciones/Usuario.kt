package com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones

import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.ImagenWithoutHash

data class Usuario(
    val email: String,
    val id: String,
    val imagen: ImagenWithoutHash,
    val nombre: String
)
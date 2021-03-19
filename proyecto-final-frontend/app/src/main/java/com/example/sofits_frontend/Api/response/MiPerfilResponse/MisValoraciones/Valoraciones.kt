package com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones

data class Valoraciones(
    val id: Id,
    val nota: Int,
    val usuarioValorador: Usuario
)
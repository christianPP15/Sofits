package com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones

data class MisValoracionesResponse(
    val media: Double,
    val usuarioValorado: Usuario,
    val valoraciones: List<Valoraciones>
)
package com.example.sofits_frontend.Api.response

data class RegisterResponse(
    val refreshToken: String,
    val token: String,
    val user: UserRegisterResponse
)
data class UserRegisterResponse(
    val email: String,
    val fechaNacimiento: String,
    val id: String,
    val imagen: Imagen,
    val nombre: String
)
data class Imagen(
    val id: String,
    val idImagen: String
)
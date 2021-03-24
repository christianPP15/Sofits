package com.example.sofits_frontend.Api.response.AuthResponse

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
    val roles: String,
    val nombre: String
)
data class Imagen(
    val id: String,
    val idImagen: String
)

data class LoginResponse(
    val refreshToken: String,
    val token: String,
    val user: Userlogin
)
data class Userlogin(
    val email: String,
    val id: String,
    val nombre: String,
    val roles: String
)

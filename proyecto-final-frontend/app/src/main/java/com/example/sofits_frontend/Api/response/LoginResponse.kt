package com.example.sofits_frontend.Api.response

data class LoginResponse(
    val refreshToken: String,
    val token: String,
    val user: Userlogin
)
data class Userlogin(
    val email: String,
    val id: String,
    val nombre: String
)

package com.example.sofits_frontend.Api.request

data class LoginRequest(
    val username:String,
    val password:String
)
data class RegisterRequest(
    var email:String,
    var nombre: String,
    val password: String,
    val password2: String,
    var fechaNacimiento: String
)
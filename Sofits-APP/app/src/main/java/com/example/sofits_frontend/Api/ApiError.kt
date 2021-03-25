package com.example.sofits_frontend.Api

data class ApiError(
    val estado: String,
    val fecha: String,
    val mensaje: String
)
data class ApiValidationSubError(
    val campo : String,
    val valorRechazado : Any?,
    val mensaje : String?
)
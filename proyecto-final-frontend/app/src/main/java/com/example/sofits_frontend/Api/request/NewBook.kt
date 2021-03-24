package com.example.sofits_frontend.Api.request

data class NuevoEjemplarRequest(
    val DescripccionLibro:String,
    val estado:String,
    val idioma:String,
    val edicion:String
)
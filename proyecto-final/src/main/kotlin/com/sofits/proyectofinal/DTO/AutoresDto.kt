package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Autor
import java.util.*

data class AutoresDto(
    val id:UUID,
    val nombre:String,
    val libros: List<LibroDtoInicio> = mutableListOf()
)

fun Autor.toDto() = AutoresDto(id!!,nombre,libros.map { it.toDtoAutor() })

package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.Usuario
import java.util.*

data class LibroDtoInicio(
    val id:UUID,
    val titulo:String,
    val usuario:List<UserLibroDto> = mutableListOf()
)
data class LibroDtoDetailAutor(
    val id: UUID,
    val titulo: String
)
fun Libro.toDtoAutor(): LibroDtoInicio = LibroDtoInicio(id!!,titulo,libroUsuario.map { it.usuarioLibro.toDtoLibro() })


fun Libro.toDetailAutor() = LibroDtoDetailAutor(id!!,titulo)
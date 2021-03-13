package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.Usuario
import java.util.*
import javax.validation.constraints.NotBlank

data class LibroDtoInicio(
    val id:UUID,
    val titulo:String,
    val usuario:List<UserLibroDto> = mutableListOf()
)
data class LibroDtoDetailAutor(
    val id: UUID,
    val titulo: String
)
data class LibroDetail(
    val id: UUID,
    val titulo: String,
    val descripcion:String?,
    val autor:AutorDeLibro
)
data class createLibro(
    @get:NotBlank(message = "{titulo.libro.NotBlank}")
    val titulo: String,
    val descripcion: String?
)
fun Libro.toDtoAutor(): LibroDtoInicio = LibroDtoInicio(id!!,titulo,libroUsuario.map { it.usuarioLibro.toDtoLibro() })


fun Libro.toDetailAutor() = LibroDtoDetailAutor(id!!,titulo)

fun Libro.toDetailLibro() = LibroDetail(id!!,titulo,descripcion,autor!!.toLibroCreate())
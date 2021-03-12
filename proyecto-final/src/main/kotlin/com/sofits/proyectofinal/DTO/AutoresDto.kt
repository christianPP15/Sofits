package com.sofits.proyectofinal.DTO

import com.fasterxml.jackson.annotation.JsonFormat
import com.sofits.proyectofinal.Modelos.Autor
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

data class AutoresDto(
    val id:UUID,
    val nombre:String,
    val libros: List<LibroDtoInicio> = mutableListOf()
)
data class AutoresDetail(
    val id:UUID,
    val nombre: String,
    val biografia:String?,
    @JsonFormat(pattern="yyyy-MM-dd")
    val nacimiento:LocalDate? = null,
    val libros: List<LibroDtoDetailAutor> = mutableListOf()
)
fun Autor.toDto() = AutoresDto(id!!,nombre,libros.map { it.toDtoAutor() })

fun Autor.toDetail() = AutoresDetail(id!!,nombre,Biografia, nacimiento,libros.map { it.toDetailAutor() })

data class createAutor(
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String
)
data class createAutorComplete(
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String,
    @get:NotBlank(message = "{autor.biografia.notBlank}")
    val biografia: String,
    @get:NotBlank(message = "{autor.imagen.notBlank}")
    val imagen:String,
    @get:Past(message = "{autor.fecha.anterior}")
    val nacimiento: LocalDate? = null
)
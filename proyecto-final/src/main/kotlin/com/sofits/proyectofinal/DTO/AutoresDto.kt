package com.sofits.proyectofinal.DTO

import com.fasterxml.jackson.annotation.JsonFormat
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
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
data class AutorDatosBiograficos(
    val id: UUID,
    val nombre: String,
    val biografia: String?=null,
    val nacimiento: String?=null,
    val imagen: ImagenWithoutHash?,
)
data class AutorDeLibro(
    val id: UUID,
    val nombre: String
)
fun Autor.toLibroCreate() = AutorDeLibro(id!!,nombre)
fun Autor.toDto() = AutoresDto(id!!,nombre,libros.map { it.toDtoAutor() })

fun Autor.toDetail() = AutoresDetail(id!!,nombre,Biografia, nacimiento,libros.map { it.toDetailAutor() })

fun Autor.toCrateDto()= AutorDatosBiograficos(id!!,nombre,Biografia,nacimiento.toString(),imagen?.toDto())

data class createAutor(
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String
)
data class createAutorComplete(
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String,
    @get:NotBlank(message = "{autor.biografia.notBlank}")
    val biografia: String,
    val nacimiento: String? = null
)
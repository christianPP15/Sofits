package com.sofits.proyectofinal.DTO

import com.fasterxml.jackson.annotation.JsonFormat
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

data class AutoresDto(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre:String,
    @ApiModelProperty(value = "Libros escritos por el autor",dataType = "LibroDtoInicio",position = 3)
    val libros: List<LibroDtoDetailAutor> = mutableListOf(),
    val imagen: ImagenWithoutHash?
)
data class AutoresDetail(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
    val biografia:String?,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
    @JsonFormat(pattern="yyyy-MM-dd")
    val nacimiento:LocalDate? = null,
    @ApiModelProperty(value = "Libros escritos por el autor",dataType = "LibroDtoInicio",position = 5)
    val libros: List<LibroDtoInicio> = mutableListOf()
)
data class AutorDatosBiograficos(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
    val biografia: String?=null,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
    @JsonFormat(pattern="yyyy-MM-dd")
    val nacimiento: String?=null,
    @ApiModelProperty(value = "Imagen del autor",dataType = "ImagenWithoutHash",position = 5)
    val imagen: ImagenWithoutHash?,
)
data class AutorDeLibro(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String
)
fun Autor.toLibroCreate() = AutorDeLibro(id!!,nombre)
fun Autor.toDto() = AutoresDto(id!!,nombre,libros.map { it.toDetailAutor() },imagen?.toDto())

fun Autor.toDetail() = AutoresDetail(id!!,nombre,Biografia, nacimiento,libros.map { it.toDtoAutor() })

fun Autor.toCrateDto()= AutorDatosBiograficos(id!!,nombre,Biografia,nacimiento.toString(),imagen?.toDto())

data class createAutor(
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 1)
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String
)
data class createAutorComplete(
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 1)
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 2)
    @get:NotBlank(message = "{autor.biografia.notBlank}")
    val biografia: String,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.lang.String",position = 4)
    val nacimiento: String? = null
)
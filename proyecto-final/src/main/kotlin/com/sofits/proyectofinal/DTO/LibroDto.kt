package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.Usuario
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.NotBlank

data class LibroDtoInicio(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo:String,
    @ApiModelProperty(value = "Usuario que lo tienen copias",dataType = "UserLibroDto",position = 3)
    val usuario:List<UserLibroDto> = mutableListOf()
)
data class LibroDtoUnidades(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo:String,
    val unidades: Int
)
data class LibroDtoDetailAutor(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo: String
)
data class LibroDetail(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID?,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo: String,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion:String?,
    @ApiModelProperty(value = "Autor del libro",dataType = "AutorDeLibro",position = 4)
    val autor:AutorDeLibro?
)
data class createLibro(
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    @get:NotBlank(message = "{titulo.libro.NotBlank}")
    val titulo: String,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String?
)
fun Libro.toDtoAutor(): LibroDtoInicio = LibroDtoInicio(id!!,titulo,libroUsuario.map { it.usuarioLibro.toDtoLibro() })

fun Libro.toDtoUnidades() = LibroDtoUnidades(id!!,titulo, libroUsuario.size)

fun Libro.toDetailAutor() = LibroDtoDetailAutor(id!!,titulo)

fun Libro.toDetailLibro() = LibroDetail(id,titulo,descripcion,autor?.toLibroCreate())
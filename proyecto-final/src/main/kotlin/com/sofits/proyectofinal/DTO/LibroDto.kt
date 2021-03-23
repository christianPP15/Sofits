package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.Usuario
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.NotBlank

/**
 * Data class que define un libro con sus ejemplares
 * @property id Identificador del libro
 * @property titulo Título del libro
 * @property usuario Usuarios que han subido ejemplares
 */
data class LibroDtoInicio(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo:String,
    @ApiModelProperty(value = "Usuario que lo tienen copias",dataType = "UserLibroDto",position = 3)
    val usuario:List<UserLibroDto> = mutableListOf()
)


/**
 * Información básica del libro
 * @property id Identificador del Libro
 * @property titulo Título del libro
 */

data class LibroDtoUnidades(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo:String,
    val unidades: Int
)
/**
 * Data class que define la información básica de un libro
 * @property titulo Título del libro
 * @property descripcion Descripción del libro
 */
data class LibroDtoDetailAutor(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo: String
)


/**
 * Data class para mostrar la información sobre las publicaciones
 * @property id Identificador del libro
 * @property titulo Título del libro
 * @property generos Generos literarios del libro
 * @property descripcion Descripción aportada sobre el libro
 * @property meGustaUsuario Indica si al usuario le gusta el libro
 */
data class LibroDtoPublicaciones(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    val titulo: String,
    @ApiModelProperty(value = "Géneros del libro",dataType = "java.lang.String",position = 3)
    val generos:String,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 4)
    val descripcion:String?,
    @ApiModelProperty(value = "Valor si al usuario a dado me gusta al libro",dataType = "java.lang.Boolean",position = 5)
    val meGustaUsuario:Boolean
)
/**
 * Data class que define datos de respuesta de un libro
 * @property id Identificador del libro
 * @property titulo Título del libro
 * @property descripcion Descripción de un libro
 * @property autor Información sobre el autor del libro
 */
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

/**
 * Data class que define la información necesaria para la creación de un libro
 * @property titulo Título para el nuevo libro
 * @property descripcion Descripción para el nuevo libro
 */
data class createLibro(
    @ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2)
    @get:NotBlank(message = "{titulo.libro.NotBlank}")
    val titulo: String,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String?
)


/**
 * Función que transforma un libro a LibroDtoInicio
 * @see LibroDtoInicio
 */
fun Libro.toDtoAutor(): LibroDtoInicio = LibroDtoInicio(id!!,titulo,libroUsuario.map { it.usuarioLibro.toDtoLibro() })

fun Libro.toDtoUnidades() = LibroDtoUnidades(id!!,titulo, libroUsuario.size)


/**
 * Función que transforma un libro a LibroDtoDetailAutor
 * @see LibroDtoDetailAutor
 */
fun Libro.toDetailAutor() = LibroDtoDetailAutor(id!!,titulo)


/**
 * Función para convertir un libro a LibroDtoPublicaciones
 * @see LibroDtoPublicaciones
 */
fun Libro.toDetailPublicacion(user:Usuario?) = LibroDtoPublicaciones(id!!,titulo,generos.map { it.nombre }.joinToString(" ,"),descripcion,likeLibroUsuario.contains(user))


/**
 * Función que transforma un libro a LibroDetail
 * @see LibroDetail
 */
fun Libro.toDetailLibro() = LibroDetail(id,titulo,descripcion,autor?.toLibroCreate())
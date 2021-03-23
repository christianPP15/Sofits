package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty

/**
 * Data class que define la respuesta de un ejemplar de un libro
 * @property libro Libro al que pertenece el ejemplar
 * @property usuario Usuario que ha subido el ejemplar
 * @property descripcion Descripción aportada por el usuario del ejemplar
 * @property estado Estado del ejemplar
 * @property edicion Nº de la edición a la que pertenece el ejemplar
 * @property intercambiado Si el libro ya a sido intercambiado o no
 * @property imagen Imagen que se aporta sobre el libro
 * @see UserLibroDto
 * @see LibroDtoDetailAutor
 * @see ImagenWithoutHash
 */
data class LibrosUsuariosResponse(
    @ApiModelProperty(value = "Libro que se ha publicado",dataType = "LibroDtoDetailAutor",position = 1)
    val libro:LibroDtoDetailAutor,
    @ApiModelProperty(value = "Usuario que ha publicado el libro",dataType = "UserLibroDto",position = 2)
    val usuario:UserLibroDto,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 4)
    val estado:String,
    @ApiModelProperty(value = "Idioma del libro",dataType = "java.lang.Int",position = 5)
    val edicion:Int,
    @ApiModelProperty(value = "El libro ha sido intercambiado o no",dataType = "java.lang.Boolean",position = 6)
    val intercambiado:Boolean,
    @ApiModelProperty(value = "Imagen del libro",dataType = "ImagenWithoutHash",position = 7)
    val imagen: ImagenWithoutHash?
)

/**
 * Data class que define los datos de un ejemplar para su creación
 * @property DescripccionLibro Descripción dada por un usuario sobre el ejemplar
 * @property estado Estado de conservación del ejemplar
 * @property idioma Idioma del ejemplar
 * @property edicion Nº de edición del ejemplar
 */
data class AgregarLibroAUsuario(
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 2)
    val DescripccionLibro:String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 3)
    val estado:String,
    @ApiModelProperty(value = "Idioma del libro",dataType = "java.lang.String",position = 4)
    val idioma:String,
    @ApiModelProperty(value = "Edición del libro que publicamos",dataType = "java.lang.String",position = 5)
    val edicion:String
)


/**
 * Función para convertir un ejemplar a LibrosUsuariosResponse
 * @see LibrosUsuariosResponse
 */
fun UsuarioTieneLibro.toDto()= LibrosUsuariosResponse(libroUsuario.toDetailAutor(),usuarioLibro.toDtoLibro(),DescripccionLibro,estado,edicion,intercambiado,imagen?.toDto())



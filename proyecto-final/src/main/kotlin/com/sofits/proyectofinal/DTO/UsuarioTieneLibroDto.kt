package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty

/**
 * Data class que define la respuesta de un ejemplar de un libro
 * @property id Identificador formado por el del libro y el del usuario
 * @property usuario Usuario que ha subido el ejemplar
 * @property descripcion Descripción aportada por el usuario del ejemplar
 * @property estado Estado del ejemplar
 * @property idioma Idioma del ejemplar
 * @property edicion Nº de la edición a la que pertenece el ejemplar
 * @property intercambiado Si el libro ya a sido intercambiado o no
 * @property imagen Imagen que se aporta sobre el libro
 * @see UserLibroDto
 * @see LibroDtoDetailAutor
 * @see ImagenWithoutHash
 */
data class LibrosUsuariosResponse(
    @ApiModelProperty(value = "Usuario que ha publicado el libro",dataType = "UserLibroDto",position = 2)
    val usuario:UserLibroDto,
    @ApiModelProperty(value = "Identificador de la publicación",dataType = "java.lang.UUID",position = 1)
    val id: UsuarioTieneLibroId,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 4)
    val estado:String,
    @ApiModelProperty(value = "Edición del libro",dataType = "java.lang.Int",position = 5)
    val edicion:Int,
    @ApiModelProperty(value = "Idioma del libro",dataType = "String",position = 8)
    val idioma:String,
    @ApiModelProperty(value = "El libro ha sido intercambiado o no",dataType = "java.lang.Boolean",position = 6)
    val intercambiado:Boolean,
    @ApiModelProperty(value = "Imagen del libro",dataType = "ImagenWithoutHash",position = 7)
    val imagen: ImagenWithoutHash?
)
/**
 * Data class que define la respuesta de un ejemplar de un libro con su información
 * @property libro Libro al que pertenece el ejemplar
 * @property usuario Usuario que ha subido el ejemplar
 * @property descripcion Descripción aportada por el usuario del ejemplar
 * @property estado Estado del ejemplar
 * @property id Identificador formado por el del libro y el del usuario
 * @property edicion Nº de la edición a la que pertenece el ejemplar
 * @property intercambiado Si el libro ya a sido intercambiado o no
 * @property idioma Idioma del libro
 * @property imagen Imagen que se aporta sobre el libro
 * @see UserLibroDto
 * @see LibroDtoDetailAutor
 * @see ImagenWithoutHash
 */
data class LibrosUsuariosResponseDetail(
    @ApiModelProperty(value = "Usuario que ha publicado el libro",dataType = "UserLibroDto",position = 2)
    val usuario:UserLibroDto,
    @ApiModelProperty(value = "Libro sobre el que se ha publicado",dataType = "LibroDtoDetailAutor",position = 8)
    val libro : LibroDtoDetailAutor,
    @ApiModelProperty(value = "Identificador de la publicación",dataType = "java.lang.UUID",position = 1)
    val id: UsuarioTieneLibroId,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 4)
    val estado:String,
    @ApiModelProperty(value = "Edición del libro",dataType = "java.lang.Int",position = 5)
    val edicion:Int,
    @ApiModelProperty(value = "Idioma del libro",dataType = "String",position = 8)
    val idioma:String,
    @ApiModelProperty(value = "El libro ha sido intercambiado o no",dataType = "java.lang.Boolean",position = 6)
    val intercambiado:Boolean,
    @ApiModelProperty(value = "Imagen del libro",dataType = "ImagenWithoutHash",position = 7)
    val imagen: ImagenWithoutHash?
)

/**
 * Data class que define la respuesta de un ejemplar de un libro con su información
 * @property libro Libro al que pertenece el ejemplar
 * @property usuario Usuario que ha subido el ejemplar
 * @property descripcion Descripción aportada por el usuario del ejemplar
 * @property estado Estado del ejemplar
 * @property id Identificador formado por el del libro y el del usuario
 * @property edicion Nº de la edición a la que pertenece el ejemplar
 * @property intercambiado Si el libro ya a sido intercambiado o no
 * @property imagen Imagen que se aporta sobre el libro
 * @property idioma Idioma del libro
 * @see UserLibroDto
 * @see LibroDtoDetailAutor
 * @see ImagenWithoutHash
 */
data class LibrosUsuariosResponseMy(
    @ApiModelProperty(value = "Usuario que ha publicado el libro",dataType = "UserLibroDto",position = 2)
    val libro: LibroDtoDetailAutor,
    @ApiModelProperty(value = "Usuario que ha publicado el libro",dataType = "UserLibroDto",position = 2)
    val usuario:UserLibroDto,
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3)
    val descripcion: String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 4)
    val estado:String,
    @ApiModelProperty(value = "Edición del libro",dataType = "java.lang.Int",position = 5)
    val edicion:Int,
    @ApiModelProperty(value = "Idioma del libro",dataType = "String",position = 8)
    val idioma:String,
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
 * Datos para editar un usuario
 * @property DescripccionLibro Descripción del ejemplar
 * @property estado Estado de conservación del libro
 * @property idioma Idioma del ejemplar
 * @property edicion Edición del ejemplar
 */
data class EditarLibroAUsuario(
    @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 2)
    val DescripccionLibro:String,
    @ApiModelProperty(value = "Estado de conservación del libro",dataType = "java.lang.String",position = 3)
    val estado:String,
    @ApiModelProperty(value = "Idioma del libro",dataType = "java.lang.String",position = 4)
    val idioma:String,
    @ApiModelProperty(value = "Edición del libro que publicamos",dataType = "java.lang.String",position = 5)
    val edicion:String,
)

/**
 * Data class que define la respuesta de una publicación
 * @property libro Libro sobre el que consultamos
 * @property publicaciones Lista de publicaciones
 */
data class PublicacionesResponse(
    val libro:LibroDtoPublicaciones,
    val publicaciones:List<LibrosUsuariosResponse>
)

/**
 * Data class que define la respuesta de una publicación
 * @property libro Libro sobre el que consultamos
 * @property publicaciones Lista de publicaciones
 */
data class PublicacionesResponseDetail(
    val libro:LibroDtoDetailAutor,
    val publicaciones:List<LibrosUsuariosResponse>
)

/**
 * Función para convertir un ejemplar a LibrosUsuariosResponseMy
 * @see LibrosUsuariosResponseMy
 */
fun UsuarioTieneLibro.toDtoMyBook() =LibrosUsuariosResponseMy(libroUsuario.toDetailAutor(),usuarioLibro.toDtoLibro(),DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())


/**
 * Función para convertir un ejemplar a LibrosUsuariosResponseMy
 * @see LibrosUsuariosResponseMy
 */
fun UsuarioTieneLibro.toDtoMyBook() =LibrosUsuariosResponseMy(libroUsuario.toDetailAutor(),usuarioLibro.toDtoLibro(),DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())
/**
 * Función para convertir un ejemplar a LibrosUsuariosResponse
 * @see LibrosUsuariosResponse
 */

fun UsuarioTieneLibro.toDtoAux() =LibrosUsuariosResponse(usuarioLibro.toDtoLibro(),id,DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())
/**
 * Función para convertir un ejemplar a LibrosUsuariosResponseDetail
 * @see LibrosUsuariosResponseDetail
 */
fun UsuarioTieneLibro.toDetalleDto() = LibrosUsuariosResponseDetail(usuarioLibro.toDtoLibro(),libroUsuario.toDetailAutor(),id,DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())



package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty


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
data class PublicacionesResponse(
    val libro:LibroDtoPublicaciones,
    val publicaciones:List<LibrosUsuariosResponse>
)
fun UsuarioTieneLibro.toDtoAux() =LibrosUsuariosResponse(usuarioLibro.toDtoLibro(),id,DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())

fun UsuarioTieneLibro.toDetalleDto() = LibrosUsuariosResponseDetail(usuarioLibro.toDtoLibro(),libroUsuario.toDetailAutor(),id,DescripccionLibro,estado,edicion,idioma,intercambiado,imagen?.toDto())



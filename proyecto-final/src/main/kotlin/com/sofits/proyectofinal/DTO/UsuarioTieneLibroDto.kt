package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty
import java.util.*


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
    val imagenWithoutHash: ImagenWithoutHash?
)
data class AgregarLibroAUsuario(
    @ApiModelProperty(value = "Identificador del libro que agregar",dataType = "java.util.UUID",position = 1)
    val idLibro:UUID,
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

fun UsuarioTieneLibro.toDto()= LibrosUsuariosResponse(libroUsuario.toDetailAutor(),usuarioLibro.toDtoLibro(),DescripccionLibro,estado,edicion,intercambiado,imagen?.toDto())



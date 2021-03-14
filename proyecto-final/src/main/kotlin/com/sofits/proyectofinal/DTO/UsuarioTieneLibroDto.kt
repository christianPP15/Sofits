package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import java.util.*


data class LibrosUsuariosResponse(
    val libro:LibroDtoDetailAutor,
    val usuario:UserLibroDto,
    val descripcion: String,
    val estado:String,
    val edicion:Int,
    val intercambiado:Boolean
)
data class AgregarLibroAUsuario(
    val idLibro:UUID,
    val DescripccionLibro:String,
    val estado:String,
    val idioma:String,
    val edicion:String
)

data class EditarLibroAUsuario(
    val DescripccionLibro:String,
    val estado:String,
    val idioma:String,
    val edicion:String,
)

fun UsuarioTieneLibro.toDto()= LibrosUsuariosResponse(libroUsuario.toDetailAutor(),usuarioLibro.toDtoLibro(),DescripccionLibro,estado,edicion,intercambiado)



package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import java.util.*

data class LibrosUsuariosResponse(
    val id:UsuarioTieneLibroId,
    val usuario:UserDTO,
    val libro: LibroDetail,
    val descripcion: String,
    val estado:String,
    val edicion:Int,
    val intercambiado:Boolean
)

fun UsuarioTieneLibro.toDto()= LibrosUsuariosResponse(id,usuarioLibro.toUserDTO(),libroUsuario.toDetailLibro(),DescripccionLibro,estado,edicion,intercambiado)

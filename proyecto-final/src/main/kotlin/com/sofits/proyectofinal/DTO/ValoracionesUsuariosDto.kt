package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.ValoracionesUsuarios
import com.sofits.proyectofinal.Modelos.ValoracionesUsuariosId

data class ValoracionDto(
    val id:ValoracionesUsuariosId,
    val usuarioValorador:UserDTOlogin,
    val usuarioValorado:UserDTOlogin,
    val nota:Int
)

data class ValoracionDtoResult(
    val valoraciones:List<ValoracionDto>,
    val media:Double
)

fun ValoracionesUsuarios.toDto() = ValoracionDto(id,usuarioValorado.UserDTOLogin(),usuarioAValorar.UserDTOLogin(),nota)
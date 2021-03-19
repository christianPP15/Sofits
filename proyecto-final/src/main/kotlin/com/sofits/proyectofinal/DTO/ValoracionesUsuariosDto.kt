package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.ValoracionesUsuarios
import com.sofits.proyectofinal.Modelos.ValoracionesUsuariosId
import io.swagger.annotations.ApiModelProperty

data class ValoracionDto(
    @ApiModelProperty(value = "Identificador de la valoración formado por el id del usuario valorador y el valorado",dataType = "ValoracionesUsuariosId",position = 1)
    val id:ValoracionesUsuariosId,
    @ApiModelProperty(value = "Usuario valorador",dataType = "UserDTOlogin",position = 2)
    val usuarioValorador:UserDTOlogin,
    @ApiModelProperty(value = "Nota de la evaluación",dataType = "java.lang.Int",position = 4)
    val nota:Int
)

data class ValoracionDtoResult(
    @ApiModelProperty(value = "Lista con todas las valoraciones de un usuario",dataType = "List<ValoracionDto>",position = 1)
    val valoraciones:List<ValoracionDto>,
    @ApiModelProperty(value = "Nota media de todas las evaluaciones",dataType = "java.lang.Double",position = 2)
    val media:Double,
    val usuarioValorado:UserDTOlogin
)

fun ValoracionesUsuarios.toDto() = ValoracionDto(id,usuarioValorado.UserDTOLogin(),nota)
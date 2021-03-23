package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.ValoracionesUsuarios
import com.sofits.proyectofinal.Modelos.ValoracionesUsuariosId
import io.swagger.annotations.ApiModelProperty

/**
 * Data class que muestra una valoracion a un usuario
 * @property id Identificador compuesto de ambos usuarios
 * @property usuarioValorador Información del usuario valorador
 * @property nota Valoración dada al usuario
 * @see UserDTOlogin
 * @see ValoracionesUsuariosId
 */
data class ValoracionDto(
    @ApiModelProperty(value = "Identificador de la valoración formado por el id del usuario valorador y el valorado",dataType = "ValoracionesUsuariosId",position = 1)
    val id:ValoracionesUsuariosId,
    @ApiModelProperty(value = "Usuario valorado",dataType = "UserDTOlogin",position = 2)
    val usuarioValorador:UserDTOlogin,
    @ApiModelProperty(value = "Nota de la evaluación",dataType = "java.lang.Int",position = 4)
    val nota:Int
)

/**
 * Data class que muestra la información de un usuario con todas sus valoraciones y quienes se lo han dado
 * @property valoraciones Usuario que han valorado
 * @property media Media de todas las valoraciones
 * @property usuarioValorado Usuario que se ha valorado
 * @see ValoracionDto
 * @see UserDTOLogin
 */
data class ValoracionDtoResult(
    @ApiModelProperty(value = "Lista con todas las valoraciones de un usuario",dataType = "List<ValoracionDto>",position = 1)
    val valoraciones:List<ValoracionDto>,
    @ApiModelProperty(value = "Nota media de todas las evaluaciones",dataType = "java.lang.Double",position = 2)
    val media:Double,
    val usuarioValorado:UserDTOlogin
)

fun ValoracionesUsuarios.toDto() = ValoracionDto(id,usuarioValorado.UserDTOLogin(),nota)
package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.ValoracionDto
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.ValoracionUsuariosServicio
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/valoraciones")
class ValoracionesController (val valoracionesServicio: ValoracionUsuariosServicio){

    @ApiOperation(value = "Obtener las valoraciones para un usuario",
        notes = "Este controlador permite obtener las valoraciones para un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = ValoracionDto::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/{id}")
    fun obtenerValoracionesParaUnUsuario(@ApiParam(value = "Identificador del usuario al que consultar sus valoraciones", required = true,type = "string")
                                         @PathVariable("id") id:UUID) =
        ResponseEntity.ok(valoracionesServicio.obtenerValoracionesDeUnUsuario(id))

    @ApiOperation(value = "Obtener si existe o no una valoraciones para un cierto usuario",
        notes = "Este controlador permite obtener si existe o no una valoración del usuario logueado hacia otro")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = ValoracionDto::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/{id}/exits")
    fun userLikeOtherUser(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                          @AuthenticationPrincipal user:Usuario?,
                          @ApiParam(value = "Identificador del usuario al que consultar si has valorado", required = true,type = "string")
                          @PathVariable("id") id:UUID ) =
        ResponseEntity
            .ok(user.let { valoracionesServicio.userLikeOtherUser(user!!,id) })

    @ApiOperation(value = "Agregar valoración",
        notes = "Este controlador permite agregar una nueva valoración a un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = ValoracionDto::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])

    @PostMapping("/{id}")
    fun addValoracion(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                      @AuthenticationPrincipal user: Usuario?,
                      @ApiParam(value = "Identificador del usuario al que agregar la valoraciones", required = true,type = "string")
                      @PathVariable("id") id: UUID,
                      @ApiParam(value = "Nota que agregar al usuario", required = true,type = "int")
                      @RequestPart nota:String): ResponseEntity<ValoracionDto> =
         ResponseEntity.status(201).body(user.let { valoracionesServicio.addValoracion(user!!,id,nota.toInt()) })



    @ApiOperation(value = "Editar valoración",
        notes = "Este controlador permite editar la valoración a un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = ValoracionDto::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PutMapping("/{id}")
    fun updateValoracion(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                         @AuthenticationPrincipal user: Usuario?,
                         @ApiParam(value = "Identificador del usuario al que editar las valoraciones", required = true,type = "string")
                         @PathVariable("id") id: UUID,
                         @ApiParam(value = "Nota nueva del usuario", required = true,type = "int")
                         @RequestPart nota:String)=
        ResponseEntity.ok( user.let { valoracionesServicio.updateValoracion(user!!,id,nota.toInt()) })

    @ApiOperation(value = "Eliminar una valoración",
        notes = "Este controlador permite eliminar la valoración a un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "No Content"),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @DeleteMapping("/{id}")
    fun deleteValoracion(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                         @AuthenticationPrincipal user: Usuario?,
                         @ApiParam(value = "Identificador del usuario al que eliminar la valoracion", required = true,type = "string")
                         @PathVariable("id") id: UUID): ResponseEntity<Any> {
        user.let { valoracionesServicio.deleteValoracion(user!!,id) }
        return ResponseEntity.noContent().build()
    }

}

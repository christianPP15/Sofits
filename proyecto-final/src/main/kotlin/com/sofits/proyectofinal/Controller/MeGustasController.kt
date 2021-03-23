package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.AutoresDetail
import com.sofits.proyectofinal.DTO.LibroDetail
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/fav")
class MeGustasController(private val usuarioService: UserService) {

    
    @ApiOperation(value = "Agregar me gusta a un libro por su id",
        notes = "Este controlador permite agregar un me gusta a un libro, indicando que usuario ha dado me gusta y a que libro")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = LibroDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PostMapping("/libro/{id}")
    fun addMeGustaLibro(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                        @AuthenticationPrincipal user: Usuario?,
                        @ApiParam(value = "Identificador del libro tipo UUID para buscarlo", required = true,type = "string")
                        @PathVariable("id") id:UUID) =
         ResponseEntity.status(201).body(user.let { usuarioService.addMeGustaLibro(user!!,id) })

    @ApiOperation(value = "Eliminar me gusta a un libro por su id",
        notes = "Este controlador permite eliminar un me gusta a un libro, indicando que usuario ha dado me gusta y a que libro")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "No Content"),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @DeleteMapping("/libro/{id}")
    fun removeMeGustaLibro(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                           @AuthenticationPrincipal user: Usuario?,
                           @ApiParam(value = "Identificador del libro tipo UUID para buscarlo", required = true,type = "string")
                           @PathVariable("id") id:UUID): ResponseEntity<Any> {
        user.let { usuarioService.removeMeGustaLibro(user!!,id) }
        return ResponseEntity.noContent().build()
    }

    @ApiOperation(value = "Agregar me gusta a un autor por su id",
        notes = "Este controlador permite agregar un me gusta a un autor, indicando que usuario ha dado me gusta y a que autor")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = AutoresDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PostMapping("/autor/{id}")
    fun addMeGustaAutor(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                        @AuthenticationPrincipal user:Usuario?,
                        @ApiParam(value = "Identificador del autor tipo UUID para buscarlo", required = true,type = "string")
                        @PathVariable("id") id:UUID): ResponseEntity<Any>{
        return ResponseEntity.status(201).body(user.let { usuarioService.addMeGustaAutor(user!!,id) })
    }
    @ApiOperation(value = "Eliminar me gusta a un autor por su id",
        notes = "Este controlador permite eliminar un me gusta a un autor, indicando que usuario ha dado me gusta y a que autor")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "No Content"),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @DeleteMapping("/autor/{id}")
    fun removeMeGustaAutor(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                           @AuthenticationPrincipal user:Usuario?,
                           @ApiParam(value = "Identificador del autor tipo UUID para buscarlo", required = true,type = "string")
                           @PathVariable("id") id:UUID): ResponseEntity<Any>{
        user.let { usuarioService.removeMeGustaAutor(user!!,id) }
        return ResponseEntity.noContent().build()
    }

    @ApiOperation(value = "Obtener los libros favoritos de un usuario",
        notes = "Este controlador permite obtener todos los libros favoritos de un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response =LibroDetail::class ),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/libro")
    fun misLibrosMeGusta(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                         @AuthenticationPrincipal user: Usuario?) = ResponseEntity.ok().body(user.let { usuarioService.misLibrosFavoritos(user!!) })

    @ApiOperation(value = "Obtener los autores favoritos de un usuario",
        notes = "Este controlador permite obtener todos los autores favoritos de un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = AutoresDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/autor")
    fun misAutoresMeGusta(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                          @AuthenticationPrincipal user: Usuario?) = ResponseEntity.ok().body(user.let { usuarioService.misAutoresFavoritos(user!!) })

}
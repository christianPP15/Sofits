package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.AutoresDto
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.Modelos.GeneroLiterario
import com.sofits.proyectofinal.Servicios.GenerosService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/generos")
class GenerosController (val generosService: GenerosService){

    @ApiOperation(
        value = "Obtener todos los géneros publicados",
        notes = "Este controlador permite obtener todos los géneros de la aplicación si no existen devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = GeneroLiterario::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/")
    fun getAllGender() = ResponseEntity.ok(generosService.getAllGeneros())

    @ApiOperation(
        value = "Obtener un género literario en base a su id",
        notes = "Este controlador permite obtener un género de la aplicación si no existen devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = GeneroLiterario::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/{id}")
    fun getGeneroById(
        @ApiParam(value = "Atributo que indica obtiene el id del autor de la ruta de la petición", required = true, type = "string")
        @PathVariable("id") id:UUID) = ResponseEntity.ok(generosService.getGeneroById(id))

    @ApiOperation(
        value = "Crear un género literario",
        notes = "Este controlador permite crear un género"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = GeneroLiterario::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PostMapping("/")
    fun addGenero(
        @ApiParam(value = "Atributo que indica el nombre del nuevo género", required = true, type = "string")
        @RequestPart nombre:String) = ResponseEntity.ok(generosService.addGenero(nombre))

    @ApiOperation(
        value = "Editar un género literario en base a su id",
        notes = "Este controlador permite editar un género de la aplicación si no existen devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = GeneroLiterario::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PutMapping("/{id}")
    fun editGenero(
        @ApiParam(value = "Atributo que indica obtiene el id del autor de la ruta de la petición", required = true, type = "string")
        @PathVariable("id") id: UUID,
        @ApiParam(value = "Atributo que indica el nombre del nuevo género", required = true, type = "string")
        @RequestPart nombre:String) = ResponseEntity.ok(generosService.editGenero(id,nombre))

    @ApiOperation(
        value = "Eliminar un género literario en base a su id",
        notes = "Este controlador permite eliminar un género de la aplicación"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok"),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteGenero(@ApiParam(value = "Atributo que indica obtiene el id del autor de la ruta de la petición", required = true, type = "string")
                     @PathVariable("id") id: UUID) : ResponseEntity<Any>{
        generosService.deleteGenero(id)
      return  ResponseEntity.noContent().build()
    }
}
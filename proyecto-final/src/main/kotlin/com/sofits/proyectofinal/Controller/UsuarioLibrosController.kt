package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.AgregarLibroAUsuario
import com.sofits.proyectofinal.DTO.EditarLibroAUsuario
import com.sofits.proyectofinal.DTO.LibrosUsuariosResponse
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.ImagenServicio
import com.sofits.proyectofinal.Servicios.UsuarioTieneLibroServicio
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/user/book")
class UsuarioLibrosController(val usuarioTieneLibroServicio: UsuarioTieneLibroServicio) {

    @ApiOperation(value = "Obtener los libros de un usuario",
        notes = "Este controlador permite obtener todos los libros de un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = LibrosUsuariosResponse::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/")
    fun getMyBooks(@PageableDefault(size = 10,page = 0) pageable: Pageable,
                   @ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                   @AuthenticationPrincipal user: Usuario?) =
        ResponseEntity.ok(usuarioTieneLibroServicio.getMyBooks(pageable,user))


    @ApiOperation(value = "Obtener los ejemplares de un libro",
        notes = "Este controlador permite obtener todos los ejemplares de un libro")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = LibrosUsuariosResponse::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/{id}")
    fun getBookEquals(@ApiParam(value = "Identificador del libro", required = true,type = "string")
                      @PathVariable("id") id:UUID,
                      @PageableDefault(size = 10,page = 0) pageable: Pageable) =
        ResponseEntity.ok(usuarioTieneLibroServicio.getAllBooksEquals( id))

    @ApiOperation(value = "Agregar los ejemplares de un libro",
        notes = "Este controlador permite agregar un nuevo libro a un usuario")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created",response = LibrosUsuariosResponse::class ),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PostMapping("/{idLibro}")
    fun addBookUser(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                    @AuthenticationPrincipal user: Usuario?,
                    @PathVariable("idLibro") idLibro:UUID,
                    @ApiParam(value = "Datos sobre el libro que se va a agregar", required = true,type = "AgregarLibroAUsuario")
                    @RequestPart("libro") libro: AgregarLibroAUsuario,
                    @ApiParam(value = "Imagen que sube el usuario sobre su libro", required = true,type = "MultipartFile")
                    @RequestPart("file") file: MultipartFile)=
        ResponseEntity.status(201).body(user.let { usuarioTieneLibroServicio.addBookUser(user!!,idLibro,libro,file) })



    @ApiOperation(value = "Cambia el estado de un libro",
        notes = "Cambia el estado de un libro, es decir, marca un libro como intercambiado o no")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "No content"),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PutMapping("/{id}/estado")
    fun changeEstado(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                     @AuthenticationPrincipal user: Usuario?,
                     @ApiParam(value = "Identificador del libro a editar", required = true,type = "string")
                     @PathVariable("id") id: UUID): ResponseEntity<Any> {
        user.let { usuarioTieneLibroServicio.changeState(user!!,id) }
        return ResponseEntity.noContent().build()
    }


    @ApiOperation(value = "Edita la información de un ejemplar",
        notes = "Edita la información de un ejemplar")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = LibrosUsuariosResponse::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PutMapping("/{id}")
    fun editComplete(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                     @AuthenticationPrincipal user: Usuario?,
                     @ApiParam(value = "Identificador del libro a editar", required = true,type = "string")
                     @PathVariable("id") id: UUID,
                     @ApiParam(value = "Información sobre el ejemplar para editarlo", required = true,type = "EditarLibroAUsuario")
                     @RequestBody libroAgregar: EditarLibroAUsuario) =
        ResponseEntity.ok(user.let { usuarioTieneLibroServicio.editLibroUser(user!!,id,libroAgregar) })

    @ApiOperation(value = "Elimina un ejemplar subida por el usuario",
        notes = "Elimina un ejemplar subida por el usuario, en base al usuario logueado y el identificador del libro")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok",response = LibrosUsuariosResponse::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @DeleteMapping("/{id}")
    fun removeBookUser(@ApiParam(value = "Usuario logueado obtenido por su token", required = true,type = "Usuario")
                       @AuthenticationPrincipal user: Usuario?,
                       @ApiParam(value = "Identificador del ejemplar a eliminar", required = true,type = "string")
                       @PathVariable("id") id: UUID) :ResponseEntity<Any>{
        user.let { usuarioTieneLibroServicio.removeBookFromUser(user!!,id) }
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{userId}/{LibroId}")
    fun removeBookUserByAdmin(@PathVariable("userId") userId:UUID,@PathVariable("LibroId") idLibro:UUID):ResponseEntity<Any>{
        usuarioTieneLibroServicio.removeBookFromUserAdmin(userId,idLibro)
        return ResponseEntity.noContent().build()
    }
}
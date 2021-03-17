package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.LibroDetail
import com.sofits.proyectofinal.DTO.UserDTOlogin
import com.sofits.proyectofinal.DTO.createLibro
import com.sofits.proyectofinal.DTO.toDetailLibro
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.ErrorControl.ApiSubError
import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Servicios.LibroService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/libro")
class LibrosController(val libroService: LibroService,private val pagination: PaginationLinksUtils) {


    @ApiOperation(value = "Obtener todos los libros públicados en la aplicación",
        notes = "Este controlador permite obtener de forma paginada una lista de los libros registrados en la aplicación, si no encuentra ningun libro devuelve un 400")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok", response =LibroDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/")
    fun getAllLibros(@PageableDefault(size = 10,page = 0) pageable: Pageable, request: HttpServletRequest) {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString())
        val result = libroService.getAllLibros(pageable)
        ResponseEntity.ok().header("link", pagination.createLinkHeader(result, uriBuilder)).body(result)
    }

    @ApiOperation(value = "Obtener un libro publicado en base a su idenficador",
        notes = "Este controlador permite obtener un libro en la aplicación, si no se encuentra disponible devuelve un 400")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok", response =LibroDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @GetMapping("/{id}")
    fun getLibroById(@ApiParam(value = "Identificador del libro tipo UUID para buscarlo", required = true,type = "string")
                     @PathVariable("id") id:UUID) = ResponseEntity.ok(libroService.getById(id))


    @ApiOperation(value = "Crear un nuevo libro",
        notes = "Este controlador permite crear un nuevo libro")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response =LibroDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PostMapping("/{id}")
    fun crearLibro(@ApiParam(value = "Identificador del autor tipo UUID para agregarlo al libro", required = true,type = "string")
                   @PathVariable("id") id: UUID,
                   @ApiParam(value = "Objeto con los datos del libro", required = true,type = "createLibro")
                   @Valid @RequestBody create: createLibro) = ResponseEntity.status(201).body(libroService.addLibro(id,create))

    @ApiOperation(value = "Editar un libro",
        notes = "Este controlador permite editar un libro")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Ok", response =LibroDetail::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @PutMapping("/{id}")
    fun editLibro(@ApiParam(value = "Identificador del libro tipo UUID para llevar a cabo su edición", required = true,type = "string")
                  @PathVariable("id") id: UUID,
                  @ApiParam(value = "Objeto con los datos del libro", required = true,type = "createLibro")
                  @Valid @RequestBody create: createLibro) = ResponseEntity.ok(libroService.editLibro(id,create))

    @ApiOperation(value = "Eliminar un libro",
        notes = "Este controlador permite eliminar un libro")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "No Content"),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiError::class)
    ])
    @DeleteMapping("/{id}")
    fun deleteLibro(@ApiParam(value = "Identificador del libro tipo UUID para llevar a cabo su borrado", required = true,type = "string") @PathVariable("id") id: UUID):ResponseEntity<Any> {
        libroService.removeLibro(id)
        return ResponseEntity.noContent().build()
    }
}
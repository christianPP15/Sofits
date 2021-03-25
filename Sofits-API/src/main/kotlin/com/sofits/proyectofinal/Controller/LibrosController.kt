package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Servicios.LibroService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.ErrorControl.SearchLibroNoResultException
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
class LibrosController(val libroService: LibroService, private val pagination: PaginationLinksUtils) {

    @ApiOperation(
        value = "Obtener todos los libros públicados en la aplicación pudiendo delimitar la busqueda por generos, autores y titulos",
        notes = "Este controlador permite obtener de forma paginada una lista de los libros dados de alta en la aplicación, si no encuentra ningun libro devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = LibroDetail::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/")
    fun getAllLibros(
        @ApiParam(value = "Título del libro", required = false, type = "string")
        @RequestParam("titulo") titulo: Optional<String>,
        @ApiParam(value = "Nombre del autor del libro", required = false, type = "string")
        @RequestParam("autor") autor: Optional<String>,
        @ApiParam(value = "Género del libro", required = false, type = "string")
        @RequestParam("genero") genero: Optional<String>,
        @ApiParam(value = "Atributo que permite la páginación de resultados", required = false, type = "Pageable")
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
        request: HttpServletRequest
    ): ResponseEntity<Page<LibroDtoUnidades?>> {
        val result: Page<Libro?> = libroService.findByArgs(titulo, autor, genero, pageable)
        if (result.isEmpty) {
            throw SearchLibroNoResultException()
        } else {
            val dtoList: Page<LibroDtoUnidades?> = result.map { it?.toDtoUnidades() }
            val uriBuilder = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString())

            return ResponseEntity.ok().header("link", pagination.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList)
        }
    }


    @ApiOperation(
        value = "Obtener un libro publicado en base a su idenficador",
        notes = "Este controlador permite obtener un libro en la aplicación, si no se encuentra disponible devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = LibroDetail::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/{id}")
    fun getLibroById(
        @ApiParam(value = "Identificador del libro tipo UUID para buscarlo", required = true, type = "string")
        @PathVariable("id") id: UUID
    ) = ResponseEntity.ok(libroService.getById(id))


    @ApiOperation(
        value = "Crear un nuevo libro",
        notes = "Este controlador permite crear un nuevo libro"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Created", response = LibroDetail::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PostMapping("/{id}")
    fun crearLibro(
        @ApiParam(
            value = "Identificador del autor tipo UUID para agregarlo al libro",
            required = true,
            type = "string"
        )
        @PathVariable("id") id: UUID,
        @ApiParam(value = "Objeto con los datos del libro", required = true, type = "createLibro")
        @Valid @RequestBody create: createLibro
    ) = ResponseEntity.status(201).body(libroService.addLibro(id, create))

    @ApiOperation(
        value = "Editar un libro",
        notes = "Este controlador permite editar un libro"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = LibroDetail::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PutMapping("/{id}")
    fun editLibro(
        @ApiParam(
            value = "Identificador del libro tipo UUID para llevar a cabo su edición",
            required = true,
            type = "string"
        )
        @PathVariable("id") id: UUID,
        @ApiParam(value = "Objeto con los datos del libro", required = true, type = "createLibro")
        @Valid @RequestBody create: createLibro
    ) = ResponseEntity.ok(libroService.editLibro(id, create))

    @ApiOperation(
        value = "Eliminar un libro",
        notes = "Este controlador permite eliminar un libro"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "No Content"),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteLibro(
        @ApiParam(
            value = "Identificador del libro tipo UUID para llevar a cabo su borrado",
            required = true,
            type = "string"
        ) @PathVariable("id") id: UUID
    ): ResponseEntity<Any> {
        libroService.removeLibro(id)
        return ResponseEntity.noContent().build()
    }

    @ApiOperation(
        value = "Agrega un género a un libro",
        notes = "Este controlador permite agregar un género a un libro"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Created", response = LibroDtoDetailAutor::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PostMapping("/{idLibro}/{idGenero}")
    fun addGeneroLibro(
        @ApiParam(
            value = "Identificador del libro tipo UUID para agregar",
            required = true,
            type = "string"
        ) @PathVariable("idLibro") idlibro: UUID, @ApiParam(
            value = "Identificador del genero tipo UUID para agregar",
            required = true,
            type = "string"
        ) @PathVariable("idGenero") idGenero: UUID
    ) =
        ResponseEntity.status(201).body(libroService.addGeneroLibro(idlibro, idGenero))

    @ApiOperation(
        value = "Elimina un género a un libro",
        notes = "Este controlador permite eliminar un género a un libro"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "No content"),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @DeleteMapping("/{idLibro}/{idGenero}")
    fun removeGeneroLibro(
        @ApiParam(
            value = "Identificador del libro tipo UUID para llevar a cabo su borrado",
            required = true,
            type = "string"
        ) @PathVariable("idLibro") idlibro: UUID, @ApiParam(
            value = "Identificador del genero tipo UUID para llevar a cabo su borrado",
            required = true,
            type = "string"
        ) @PathVariable("idGenero") idGenero: UUID
    ): ResponseEntity<Any> {
        libroService.removeGeneroLibro(idlibro, idGenero)
        return ResponseEntity.noContent().build()
    }

}

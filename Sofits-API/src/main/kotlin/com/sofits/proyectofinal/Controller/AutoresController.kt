package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.ErrorControl.ApiSubError
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.AutorService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestControllerAdvice
@RequestMapping("/autores")
class AutoresController (val autoresServicio:AutorService,private val paginationLinksUtils: PaginationLinksUtils){

    @ApiOperation(
        value = "Obtener todos los autores públicados en la aplicación pudiendo paginar la busqueda",
        notes = "Este controlador permite obtener de forma paginada una lista de los autores dados de alta en la aplicación, si no encuentra ningun autor devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = AutoresDto::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/")
    fun obtenerLibrosAutores(@ApiParam(value = "Atributo que permite la páginación de resultados", required = false, type = "Pageable")
                             @PageableDefault(size = 10,page = 0) pageable: Pageable,
                             @ApiParam(value = "Petición del usuario", required = false, type = "HttpServletRequest")
                             request: HttpServletRequest): ResponseEntity<Page<AutoresDto>> {
        val autores=autoresServicio.obtenerLibrosAutoresServicio(pageable)
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString())

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(autores, uriBuilder))
            .body(autores)
    }

    @ApiOperation(
        value = "Obtener un autor en base a su id",
        notes = "Este controlador permite obtener un autor en base a su id, si no lo encuentra devuelve un 400"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = AutoresDetailMeGusta::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @GetMapping("/{id}")
    fun obtenerDetallesAutor(
        @ApiParam(value = "Atributo que indica obtiene el id del autor de la ruta de la petición", required = true, type = "string")
        @PathVariable("id") id:UUID,
        @ApiParam(value = "Usuario que realiza la petición", required = true, type = "Usuario")
        @AuthenticationPrincipal user:Usuario?) = ResponseEntity.ok().body(
        user?.let { autoresServicio.obtenerAutor(id, it) })


    @ApiOperation(
        value = "Agregar un nuevo autor",
        notes = "Este controlador permite agregar un autor, si la validación da algun error"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Created", response = AutorDatosBiograficos::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PostMapping("/")
    fun agregarAutorCompleto(
        @ApiParam(value = "Información del nuevo autor", required = true, type = "createAutorComplete")
        @Valid @RequestPart("nuevoAutor") created: createAutorComplete,
        @ApiParam(value = "Imagen subida por el usuario", required = true, type = "MultipartFile")
        @RequestPart("file") file: MultipartFile)=
        ResponseEntity.status(201).body(autoresServicio.addAutorCompleto(created,file))


    @ApiOperation(
        value = "Editar un autor en base a su id",
        notes = "Este controlador permite editar un autor a partir de su id"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = AutoresDetail::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @PutMapping("/{id}")
    fun updateComplete(
        @ApiParam(value = "Información a editar del autor", required = true, type = "createAutorComplete")
        @Valid @RequestBody create: createAutorComplete,
        @ApiParam(value = "Id del autor a actualizar", required = true, type = "string")
        @PathVariable("id") id: UUID) = autoresServicio.updateComplete(id,create)

    @ApiOperation(
        value = "Elimina un autor en base a su id",
        notes = "Este controlador permite dar de baja un autor a partir de su id"
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "No Content"),
            ApiResponse(code = 401, message = "Unauthorized", response = ApiError::class),
            ApiResponse(code = 400, message = "Bad Request", response = ApiError::class)
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteAutor(
        @ApiParam(value = "Id del autor a actualizar", required = true, type = "string")
        @PathVariable("id") id: UUID) = autoresServicio.deleteAutor(id)



}
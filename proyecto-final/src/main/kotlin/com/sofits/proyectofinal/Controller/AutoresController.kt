package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.ErrorControl.ApiSubError
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.AutorService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import io.swagger.annotations.ApiOperation
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

    @GetMapping("/")
    fun obtenerLibrosAutores(@PageableDefault(size = 10,page = 0) pageable: Pageable,request: HttpServletRequest): ResponseEntity<Page<AutoresDto>> {
        val autores=autoresServicio.obtenerLibrosAutoresServicio(pageable)
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString())

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(autores, uriBuilder))
            .body(autores)
    }


    @GetMapping("/{id}")
    fun obtenerDetallesAutor(@PathVariable("id") id:UUID,@AuthenticationPrincipal user:Usuario?) = ResponseEntity.ok().body(
        user?.let { autoresServicio.obtenerAutor(id, it) })

    @GetMapping("/nombre/{nombre}")
    fun findAutorByNombre(@Param("nombre") nombre:String) = ResponseEntity.ok(autoresServicio.findByNombre(nombre))


    @PostMapping("/")
    fun agregarAutorCompleto(@Valid @RequestPart("nuevoAutor") created: createAutorComplete, @RequestPart("file") file: MultipartFile)=
        ResponseEntity.status(201).body(autoresServicio.addAutorCompleto(created,file))


    @PutMapping("/{id}")
    fun updateComplete(@Valid @RequestBody create: createAutorComplete,@PathVariable("id") id: UUID) = autoresServicio.updateComplete(id,create)


    @DeleteMapping("/{id}")
    fun deleteAutor(@PathVariable("id") id: UUID) = autoresServicio.deleteAutor(id)



}
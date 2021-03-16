package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.LibroDtoDetailAutor
import com.sofits.proyectofinal.DTO.createLibro
import com.sofits.proyectofinal.DTO.toDetailAutor
import com.sofits.proyectofinal.ErrorControl.SearchProductoNoResultException
import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Servicios.LibroService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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
class LibrosController(val libroService: LibroService) {
    @Autowired
    lateinit var paginationLinksUtils: PaginationLinksUtils

    @GetMapping("/")
    fun getAllLibros(@RequestParam("titulo") titulo:Optional<String>,
                     @RequestParam("autor") autor:Optional<String>,
                     @RequestParam("genero") genero:Optional<String>,
                     @PageableDefault(size = 10,page = 0) pageable: Pageable,
                     request: HttpServletRequest): ResponseEntity<Page<LibroDtoDetailAutor>> {
        val result: Page<Libro?> = libroService.findByArgs(titulo,autor,genero,pageable)
        if (result.isEmpty){
            throw SearchProductoNoResultException()
        }else{
            val dtoList: Page<LibroDtoDetailAutor> = result.map{ it?.toDetailAutor() }
            val uriBuilder = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString())

            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList)
        }
    }

    @GetMapping("/{id}")
    fun getLibroById(@PathVariable("id") id:UUID) = libroService.getById(id)

    @PostMapping("/{id}")
    fun crearLibro(@PathVariable("id") id: UUID,@Valid @RequestBody create: createLibro) = libroService.addLibro(id,create)

    @PutMapping("/{id}")
    fun editLibro(@PathVariable("id") id: UUID, @Valid @RequestBody create: createLibro) = libroService.editLibro(id,create)

    @DeleteMapping("/{id}")
    fun deleteLibro(@PathVariable("id") id: UUID) = libroService.removeLibro(id)
}
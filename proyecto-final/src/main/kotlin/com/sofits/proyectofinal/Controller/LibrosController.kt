package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.createLibro
import com.sofits.proyectofinal.Servicios.LibroService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/libro")
class LibrosController(val libroService: LibroService) {

    @GetMapping("/")
    fun getAllLibros(@PageableDefault(size = 10,page = 0) pageable: Pageable) =  libroService.getAllLibros(pageable)

    @GetMapping("/{id}")
    fun getLibroById(@PathVariable("id") id:UUID) = libroService.getById(id)

    @PostMapping("/{id}")
    fun crearLibro(@PathVariable("id") id: UUID,@Valid @RequestBody create: createLibro) = libroService.addLibro(id,create)

    @PutMapping("/{id}")
    fun editLibro(@PathVariable("id") id: UUID, @Valid @RequestBody create: createLibro) = libroService.editLibro(id,create)

    @DeleteMapping("/{id}")
    fun deleteLibro(@PathVariable("id") id: UUID) = libroService.removeLibro(id)
}
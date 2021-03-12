package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.createAutor
import com.sofits.proyectofinal.DTO.createAutorComplete
import com.sofits.proyectofinal.Servicios.AutorService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestControllerAdvice
@RequestMapping("/autores")
class AutoresController (val autoresServicio:AutorService){

    @GetMapping("/")
    fun obtenerLibrosAutores(@PageableDefault(size = 10,page = 0) pageable: Pageable)=
        ResponseEntity.status(200).body(autoresServicio.obtenerLibrosAutoresServicio(pageable))

    @GetMapping("/{id}")
    fun obtenerDetallesAutor(@PathVariable("id") id:UUID) = ResponseEntity.ok().body(autoresServicio.obtenerAutor(id))

    @PostMapping("/user")
    fun agregarAutorPorUsuario(@Valid @RequestBody create: createAutor) =
        ResponseEntity.status(201).body(autoresServicio.usuarioAddAutor(create))

    @PostMapping("/")
    fun agregarAutorCompleto(@Valid @RequestBody created: createAutorComplete)=
        ResponseEntity.status(201).body(autoresServicio.addAutorCompleto(created))
}
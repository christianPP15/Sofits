package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.CreateUserDTO
import com.sofits.proyectofinal.DTO.createAutor
import com.sofits.proyectofinal.DTO.createAutorComplete
import com.sofits.proyectofinal.Servicios.AutorService
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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

    @GetMapping("/{nombre}")
    fun findAutorByNombre(@Param("nombre") nombre:String) = autoresServicio.findByNombre(nombre)

    @PostMapping("/user")
    fun agregarAutorPorUsuario(@Valid @RequestBody create: createAutor) =
        ResponseEntity.status(201).body(autoresServicio.usuarioAddAutor(create))

    @PostMapping("/")
    fun agregarAutorCompleto(@Valid @RequestPart("nuevoAutor") created: createAutorComplete, @RequestPart("file") file: MultipartFile)=
        ResponseEntity.status(201).body(autoresServicio.addAutorCompleto(created,file))

    @PutMapping("/user/{id}")
    fun updateAutorPorUsuario(@Valid @RequestBody create: createAutor,@PathVariable("id") id: UUID) =
        autoresServicio.updateByUsuario(id,create)

    @PutMapping("/{id}")
    fun updateComplete(@Valid @RequestBody create: createAutorComplete,@PathVariable("id") id: UUID) = autoresServicio.updateComplete(id,create)

    @DeleteMapping("/{id}")
    fun deleteAutor(@PathVariable("id") id: UUID) = autoresServicio.deleteAutor(id)



}
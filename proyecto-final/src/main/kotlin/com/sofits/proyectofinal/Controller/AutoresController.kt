package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.Servicios.AutorService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@RequestMapping("/autores")
class AutoresController (val autoresServicio:AutorService){

    @GetMapping("/")
    fun obtenerLibrosAutores(@PageableDefault(size = 10,page = 0) pageable: Pageable)=
        ResponseEntity.status(200).body(autoresServicio.obtenerLibrosAutoresServicio(pageable))


}
package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.UsuarioTieneLibroServicio
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/book")
class UsuarioLibrosController(val usuarioTieneLibroServicio: UsuarioTieneLibroServicio) {

    @GetMapping("/")
    fun getMyBooks(@PageableDefault(size = 10,page = 0) pageable: Pageable, @AuthenticationPrincipal user: Usuario?) = usuarioTieneLibroServicio.getMyBooks(pageable,user)


}
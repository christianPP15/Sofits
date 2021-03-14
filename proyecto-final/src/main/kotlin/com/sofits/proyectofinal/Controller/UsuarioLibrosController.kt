package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.AgregarLibroAUsuario
import com.sofits.proyectofinal.DTO.EditarLibroAUsuario
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.UsuarioTieneLibroServicio
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/book")
class UsuarioLibrosController(val usuarioTieneLibroServicio: UsuarioTieneLibroServicio) {

    @GetMapping("/")
    fun getMyBooks(@PageableDefault(size = 10,page = 0) pageable: Pageable, @AuthenticationPrincipal user: Usuario?) =
        usuarioTieneLibroServicio.getMyBooks(pageable,user)

    @GetMapping("/{id}")
    fun getBookEquals(@PathVariable("id") id:UUID,@PageableDefault(size = 10,page = 0) pageable: Pageable) =
        usuarioTieneLibroServicio.getAllBooksEquals(pageable, id)

    @PostMapping("/")
    fun addBookUser(@AuthenticationPrincipal user: Usuario?,@RequestBody libro: AgregarLibroAUsuario) =
        user.let { usuarioTieneLibroServicio.addBookUser(user!!,libro) }

    @PutMapping("/{id}/estado")
    fun changeEstado(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID) =
        user.let { usuarioTieneLibroServicio.changeState(user!!,id) }

    @PutMapping("/{id}")
    fun editComplete(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID,@RequestBody libroAgregar: EditarLibroAUsuario) =
        user.let { usuarioTieneLibroServicio.editLibroUser(user!!,id,libroAgregar) }

    @DeleteMapping("/{id}")
    fun removeBookUser(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID) =
        user.let { usuarioTieneLibroServicio.removeBookFromUser(user!!,id) }
}
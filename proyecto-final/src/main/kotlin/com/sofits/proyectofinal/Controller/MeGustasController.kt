package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/fav")
class MeGustasController(private val usuarioService: UserService) {

    @PostMapping("/libro/{id}")
    fun addMeGustaLibro(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id:UUID) = user.let { usuarioService.addMeGustaLibro(user!!,id) }

    @DeleteMapping("/libro/{id}")
    fun removeMeGustaLibro(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id:UUID) = user.let { usuarioService.removeMeGustaLibro(user!!,id) }

    @PostMapping("/autor/{id}")
    fun addMeGustaAutor(@AuthenticationPrincipal user:Usuario?, @PathVariable("id") id:UUID)= user.let { usuarioService.addMeGustaAutor(user!!,id) }

    @DeleteMapping("/autor/{id}")
    fun removeMeGustaAutor(@AuthenticationPrincipal user:Usuario?, @PathVariable("id") id:UUID)= user.let { usuarioService.removeMeGustaAutor(user!!,id) }

    @GetMapping("/libro")
    fun misLibrosMeGusta(@AuthenticationPrincipal user: Usuario?) = user.let { usuarioService.misLibrosFavoritos(user!!) }

    @GetMapping("/autor")
    fun misAutoresMeGusta(@AuthenticationPrincipal user: Usuario?) = user.let { usuarioService.misAutoresFavoritos(user!!) }

}
package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.ValoracionUsuariosServicio
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/valoraciones")
class ValoracionesController (val valoracionesServicio: ValoracionUsuariosServicio){

    @GetMapping("/{id}")
    fun obtenerValoracionesParaUnUsuario(@PathVariable("id") id:UUID) =valoracionesServicio.obtenerValoracionesDeUnUsuario(id)

    @GetMapping("/{id}")
    fun userLikeOtherUser(@AuthenticationPrincipal user:Usuario?,@PathVariable("id") id:UUID ) =
        user.let { valoracionesServicio.userLikeOtherUser(user!!,id) }

    @PostMapping("/{id}/{nota}")
    fun addValoracion(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID,@PathVariable("nota") nota:Int) =
        user.let { valoracionesServicio.addValoracion(user!!,id,nota) }

    @PutMapping("/{id}/{nota}")
    fun updateValoracion(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID,@PathVariable("nota") nota:Int)=
        user.let { valoracionesServicio.updateValoracion(user!!,id,nota) }

    @DeleteMapping("/{id}")
    fun deleteValoracion(@AuthenticationPrincipal user: Usuario?,@PathVariable("id") id: UUID) =
        user.let { valoracionesServicio.deleteValoracion(user!!,id) }
}
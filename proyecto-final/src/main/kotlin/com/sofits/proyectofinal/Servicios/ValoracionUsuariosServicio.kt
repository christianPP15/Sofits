package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.ValoracionDto
import com.sofits.proyectofinal.DTO.ValoracionDtoResult
import com.sofits.proyectofinal.DTO.toDto
import com.sofits.proyectofinal.ErrorControl.UserNotFoundById
import com.sofits.proyectofinal.ErrorControl.ValoracionNotExist
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.ValoracionesUsuarios
import com.sofits.proyectofinal.Modelos.ValoracionesUsuariosId
import com.sofits.proyectofinal.Modelos.ValoracionesUsuariosRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Service
class ValoracionUsuariosServicio (val usuariosServicio: UserService) : BaseService<ValoracionesUsuarios,ValoracionesUsuariosId,ValoracionesUsuariosRepository>(){


    fun obtenerValoracionesDeUnUsuario(id:UUID): ValoracionDtoResult {
        val user = usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) }
        val result = repositorio.getValorationFromOneUser(user)
        var media=0.0
        if (result.isNotEmpty()){
            result.map { media += it.nota }
            media/=result.size
        }
        return ValoracionDtoResult(result.map { it.toDto() },media)
    }

    fun userLikeOtherUser(user: Usuario,id:UUID) =
        repositorio.userLikeAnotherUser(user,usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) })
                .orElseThrow { ValoracionNotExist(user.id!!,id) }.toDto()

    fun addValoracion(user: Usuario,id: UUID,nota:Int): ValoracionDto {
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        val valoracion= ValoracionesUsuarios(idValoracion,user,usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) },nota)
        return repositorio.save(valoracion).toDto()
    }
    fun deleteValoracion(user: Usuario,id: UUID){
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        if (repositorio.existsById(idValoracion))
            repositorio.deleteById(idValoracion)

    }

    fun updateValoracion(user: Usuario,id: UUID,nota:Int): ValoracionDto {
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        val valoracion= repositorio.findById(idValoracion).orElseThrow { ValoracionNotExist(user.id!!,id) }
        return repositorio.save(valoracion).toDto()
    }
}

package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
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

/**
 * Clase estereotipada como servicio para gestionar las valoraciones entre los usuarios, y que extiende a BaseService
 * @see Service
 * @see BaseService
 * @author christianPP15
 */
@Service
class ValoracionUsuariosServicio (
    /**
     * Servicio del usuario
     * @see UserService
     */
    val usuariosServicio: UserService) : BaseService<ValoracionesUsuarios,ValoracionesUsuariosId,ValoracionesUsuariosRepository>(){

    /**
     * Función para obtener todas las valoraciones dadas a un usuario
     * @param id Identificador del usuario que queremos consultar
     * @throws UserNotFoundById En el caso de que no encontremos un usuario con el id pasado por parámetro
     * @see ValoracionDtoResult
     * @return Una lista con las valoraciones al usuario, la media de todas las valoraciones y la información del usuario al que estamos valorando
     */
    fun obtenerValoracionesDeUnUsuario(id:UUID): ValoracionDtoResult {
        val user = usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) }
        val result = repositorio.getValorationFromOneUser(user)
        var media=0.0
        if (result.isNotEmpty()){
            result.map { media += it.nota }
            media/=result.size
        }
        return ValoracionDtoResult(result.map { it.toDto() },media,user.UserDTOLogin())
    }

    /**
     * Función para obtener la valoración de un usuario hacia el otro
     * @param user Usuario valorador
     * @param id Identificador del usuario valorado
     * @throws ValoracionNotExist Si no existen valoraciones entre esos usuarios
     * @return Valoración de un usuario a otro
     */
    fun userLikeOtherUser(user: Usuario,id:UUID) =
        repositorio.userLikeAnotherUser(user,usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) })
                .orElseThrow { ValoracionNotExist(user.id!!,id) }.toDto()

    /**
     * Agregamos una valoración a un usuario
     * @param user Usuario valorador
     * @param id Identificador del usuario valorado
     * @param nota Valor de la valoración
     * @return Valoración de un usuario a otro
     * @throws UserNotFoundById si no se encuentra al usuario valorador
     */
    fun addValoracion(user: Usuario,id: UUID,nota:Int): ValoracionDto {
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        val valoracion= ValoracionesUsuarios(idValoracion,user,usuariosServicio.findById(id).orElseThrow { UserNotFoundById(id) },nota)
        return repositorio.save(valoracion).toDto()
    }

    /**
     * Elimina una valoración entre dos usuarios
     * @param user Usuario valorador
     * @param id Identificador del usuario valorado
     */
    fun deleteValoracion(user: Usuario,id: UUID){
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        if (repositorio.existsById(idValoracion))
            repositorio.deleteById(idValoracion)

    }

    /**
     * Edita la valoración de un usuario
     * @param user Usuario valorador
     * @param id Identificador del usuario valorado
     * @throws ValoracionNotExist si no existe una valoración entre ambos usuarios
     * @return Valoración editada
     */
    fun updateValoracion(user: Usuario,id: UUID,nota:Int): ValoracionDto {
        val idValoracion= ValoracionesUsuariosId(user.id!!,id)
        val valoracion= repositorio.findById(idValoracion).orElseThrow { ValoracionNotExist(user.id!!,id) }
        valoracion.nota=nota
        return repositorio.save(valoracion).toDto()
    }
}

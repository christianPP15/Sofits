package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.ErrorControl.NoExistGender
import com.sofits.proyectofinal.ErrorControl.NoExistGenderById
import com.sofits.proyectofinal.Modelos.GeneroLiterario
import com.sofits.proyectofinal.Modelos.GeneroLiterarioRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.stereotype.Service
import java.util.*

@Service
class GenerosService : BaseService<GeneroLiterario, UUID, GeneroLiterarioRepository>() {
    /**
     * Función para obtener todos los géneros literarios
     */
    fun getAllGeneros() = repositorio.findAll().takeIf { !it.isEmpty() } ?: throw NoExistGender()

    /**
     * Función para obtener un género por su id
     * @param id Identificador del género
     */
    fun getGeneroById(id:UUID) = repositorio.findById(id).orElseThrow { NoExistGenderById(id) }

    /**
     * Función para agregar un nuevo género
     * @param nombre Nombre del nuevo género
     */
    fun addGenero(nombre:String) = repositorio.save(GeneroLiterario(nombre))

    /**
     * Función para editar un género
     * @param id Identificador del género a eliminar
     * @param nombre Nuevo nombre del género
     */
    fun editGenero(id: UUID,nombre: String) = repositorio.findById(id).map {
        it.nombre=nombre
        repositorio.save(it)
    }.orElseThrow { NoExistGenderById(id) }

    /**
     * Función para eliminar un género
     * @param id Identificador del género a eliminar
     */
    fun deleteGenero(id: UUID) = if (repositorio.existsById(id)) repositorio.deleteById(id) else null
}
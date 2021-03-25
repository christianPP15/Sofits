package com.sofits.proyectofinal.Servicios.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * Servicio base que implementa algunos de los métodos de JpaRepository
 * @see JpaRepository
 */
abstract class BaseService<T, ID, R : JpaRepository<T, ID>> {
    /**
     * Objeto genérico que actua a base de repositorio del tipo que indiquemos
     */
    @Autowired
    protected lateinit var repositorio: R

    /**
     * Guarda el objeto que genérico que se le pase por parámetro
     * @param t objeto a almacenar en la base de datos
     * @return Devuelve una instancia del objeto guardado
     */
    fun save(t: T): T {
        return repositorio!!.save(t)
    }

    /**
     * Busca el objeto en base al id proporcionado
     * @param Id Identificador de la entidad a encontrar
     * @return Devuelve una instancia del objeto encontrado
     */
    fun findById(id: ID): Optional<T> {
        return repositorio!!.findById(id)
    }

    /**
     * Obtiene todas las entidades almacenadas en la base de datos
     * @return Devuelve una lista con todos las entidades
     */
    fun findAll(): List<T> {
        return repositorio!!.findAll()
    }
    /**
     * Obtiene todas las entidades almacenadas en la base de datos de forma paginada
     * @see Pageable
     * @return Devuelve una lista con todos las entidades con la posibilidad de paginarlas
     */
    fun findAll(pageable: Pageable): Page<T> {
        return repositorio!!.findAll(pageable)
    }

    fun edit(t: T): T {
        return repositorio!!.save(t)
    }

    /**
     * Elimina la entidad que se la pasa por parámetro
     * @param t Entidad a eliminar
     */
    open fun delete(t: T) {
        repositorio!!.delete(t)
    }

    fun deleteById(id: ID) {
        repositorio!!.deleteById(id)
    }

    /**
     * Comprueba si una entidad existe en la base de datos en base a su id
     * @param id Identificador de la entidad a comprobar su existencia
     * @return Devuelve verdadero si existe y falso si no.
     */
    fun existsById(id: ID):Boolean{
        return repositorio!!.existsById(id)
    }
}
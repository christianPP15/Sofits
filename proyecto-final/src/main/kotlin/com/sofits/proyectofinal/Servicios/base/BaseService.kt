package com.sofits.proyectofinal.Servicios.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

abstract class BaseService<T, ID, R : JpaRepository<T, ID>> {
    @Autowired
    protected lateinit var repositorio: R

    fun save(t: T): T {
        return repositorio!!.save(t)
    }

    fun findById(id: ID): Optional<T> {
        return repositorio!!.findById(id)
    }

    fun findAll(): List<T> {
        return repositorio!!.findAll()
    }

    fun findAll(pageable: Pageable): Page<T> {
        return repositorio!!.findAll(pageable)
    }

    fun edit(t: T): T {
        return repositorio!!.save(t)
    }

    fun delete(t: T) {
        repositorio!!.delete(t)
    }

    fun deleteById(id: ID) {
        repositorio!!.deleteById(id)
    }

    fun existsById(id: ID):Boolean{
        return repositorio!!.existsById(id)
    }
}
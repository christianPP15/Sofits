package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.AutorsNotExists
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.Modelos.AutorRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import javax.validation.Valid

@Service
class AutorService : BaseService<Autor,UUID,AutorRepository>(){


    fun obtenerLibrosAutoresServicio(pageable: Pageable)=
        repositorio.findAll(pageable).map { it.toDto() }.takeIf { !it.isEmpty } ?: throw AutorsNotExists()


    fun obtenerAutor(id:UUID) = repositorio.findById(id).map { it.toDetail() }.takeIf { !it.isEmpty } ?: throw AutorNotExist(id)

    fun usuarioAddAutor(create: createAutor) = repositorio.save(Autor(create.nombre))

    fun addAutorCompleto(create: createAutorComplete) = repositorio.save(Autor(create.nombre,create.biografia,create.imagen,create.nacimiento))
}
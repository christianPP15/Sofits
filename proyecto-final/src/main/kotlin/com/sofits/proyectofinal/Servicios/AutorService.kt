package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.AutoresDto
import com.sofits.proyectofinal.DTO.toDto
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.Modelos.AutorRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class AutorService : BaseService<Autor,UUID,AutorRepository>(){


    fun obtenerLibrosAutoresServicio(pageable: Pageable): Page<AutoresDto> {
        return repositorio.findAll(pageable).map { it.toDto() }
    }
}
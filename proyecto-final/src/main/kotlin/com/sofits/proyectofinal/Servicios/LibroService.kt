package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.LibroDetail
import com.sofits.proyectofinal.DTO.createLibro
import com.sofits.proyectofinal.DTO.toDetailLibro
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.Modelos.AutorRepository
import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.LibroRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import com.sofits.proyectofinal.Util.PaginationLinksUtils
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class LibroService(val autorRepository: AutorRepository) : BaseService<Libro, UUID, LibroRepository>(){

    fun getAllLibros(pageable: Pageable) = repositorio.findAll(pageable).map { it.toDetailLibro() }.takeIf { !it.isEmpty } ?: throw LibrosNotExists()

    fun getById(id:UUID) = repositorio.findById(id).map { it.toDetailLibro() }.orElseThrow { LibroNotExist(id) }

    fun addLibro(id: UUID,create: createLibro): LibroDetail {
        val autor = autorRepository.findById(id).orElseThrow { AutorNotExist(id) }
        val libro=Libro(create.titulo,create.descripcion)
        autor.libros.add(libro)
        libro.autor=autor
        autorRepository.save(autor)
        repositorio.save(libro)
        return libro.toDetailLibro()
    }
    fun editLibro(id: UUID,create: createLibro) = repositorio.findById(id).map { libro->
            libro.titulo=create.titulo
            libro.descripcion=create.descripcion
            repositorio.save(libro).toDetailLibro()
        }.orElseThrow { AutorNotExist(id) }


    fun removeLibro(id: UUID) {
        val libro= repositorio.findById(id).orElseThrow { LibroNotExist(id) }
        val autor= autorRepository.findById(libro.autor!!.id!!).orElseThrow { AutorNotExist(id) }
        autor.libros.remove(libro)
        autorRepository.save(autor)
        repositorio.delete(libro)
    }
}
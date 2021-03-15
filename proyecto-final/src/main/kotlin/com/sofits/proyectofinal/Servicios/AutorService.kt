package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.AutorsNotExists
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.Modelos.AutorRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.*

import java.time.format.DateTimeFormatter


@Service
class AutorService : BaseService<Autor,UUID,AutorRepository>(){
    @Autowired
    lateinit var  servicioImagenes: ImagenServicio

    fun obtenerLibrosAutoresServicio(pageable: Pageable)=
        repositorio.findAll(pageable).map { it.toDto() }.takeIf { !it.isEmpty } ?: throw AutorsNotExists()


    fun obtenerAutor(id:UUID) = repositorio.findById(id).map { it.toDetail() }.takeIf { !it.isEmpty } ?: throw AutorNotExist(id)

    fun usuarioAddAutor(create: createAutor) = repositorio.save(Autor(create.nombre)).toCrateDto()

    fun addAutorCompleto(create: createAutorComplete,file: MultipartFile): AutorDatosBiograficos {
        val imagen = servicioImagenes.save(file)
        val autor= Autor(create.nombre,create.biografia, LocalDate.parse(create.nacimiento, DateTimeFormatter.ofPattern("d/MM/yyyy")))
        autor.imagen=imagen
        return repositorio.save(autor).toCrateDto()
    }

    fun updateByUsuario(id:UUID,update:createAutor) =
        repositorio.findById(id).map { autor->
        autor.nombre=update.nombre
            ResponseEntity.ok(repositorio.save(autor).toDetail())
    }.orElseThrow{AutorNotExist(id)}


    fun updateComplete(id: UUID,update: createAutorComplete)=
        repositorio.findById(id).map { autor->
            autor.nombre=update.nombre
            autor.Biografia=update.biografia
            autor.nacimiento= LocalDate.parse(update.nacimiento, DateTimeFormatter.ofPattern("d/MM/yyyy"))
            ResponseEntity.ok(repositorio.save(autor).toDetail())
        }.orElseThrow { AutorNotExist(id) }

    fun deleteAutor(id: UUID) : ResponseEntity<Any>{
        if (repositorio.existsById(id))
            repositorio.deleteById(id)
        return ResponseEntity.noContent().build()
    }
    fun findByNombre(nombre:String) = ResponseEntity.ok(repositorio.findByNombreIgnoreCase(nombre).map { it.toDetail() }.orElseThrow { AutorsNotExists() })
}
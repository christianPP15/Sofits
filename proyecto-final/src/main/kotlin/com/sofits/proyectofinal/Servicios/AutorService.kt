package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.AutorsNotExists
import com.sofits.proyectofinal.Modelos.*
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.*

import java.time.format.DateTimeFormatter

/**
 * Clase estereotipada como servicio que se encarga de la gestión de los autores y que extiende de BaseService
 * @author christianPP15
 * @see BaseService
 * @see Service
 */
@Service
class AutorService(
    /**
     * Repositorio de los ejemplares de un usuario
     */
    val usuarioLibro: UsuarioTieneLibroRepository,
    /**
     * Repositorio que gestiona los libros
     */
    val libroRepository: LibroRepository,
    /**
     * Repositorio que gestiona los usuarios
     */
    val userRepository: UsuarioRepository,
    /**
     * Servicio para la gestión de las imagenes
     */
    val  servicioImagenes: ImagenServicio) : BaseService<Autor,UUID,AutorRepository>(){

    /**
     * Función para obtener todos los autores activos de forma páginada
     * @param pageable Parámetro que permite la paginación de los resultados
     * @throws AutorsNotExists Si no existen autores
     * @return Devuelve un Page con los autores activos
     */
    fun obtenerLibrosAutoresServicio(pageable: Pageable)=
        repositorio.obtenerAutoresActivos(pageable).map { it.toDto() }.takeIf { !it.isEmpty } ?: throw AutorsNotExists()

    /**
     * Función para obtener un autor en base a su id
     * @param id Identificador del autor que permite su obtención
     * @throws AutorNotExist Devuelve una excepción en el caso de que el autor que se solicita no exista
     */
    fun obtenerAutor(id:UUID) = repositorio.findById(id).map { it.toDetail() }.takeIf { !it.isEmpty } ?: throw AutorNotExist(id)

    /**
     * Agregamos un nuevo autor
     * @param create Datos del autor
     * @param file imagen del autor
     * @return Nuevo autor registrado
     */
    fun addAutorCompleto(create: createAutorComplete,file: MultipartFile): AutorDatosBiograficos {
        val imagen = servicioImagenes.save(file)
        val autor= Autor(create.nombre,create.biografia, LocalDate.parse(create.nacimiento, DateTimeFormatter.ofPattern("d/MM/yyyy")))
        autor.imagen=imagen
        return repositorio.save(autor).toCrateDto()
    }

    /**
     * Editamos la información de un autor
     * @param id Identificador del autor a editar
     * @param update Datos para actualizar el usuario
     * @throws AutorNotExist Si no existe el autor que se solicita
     * @return Devuelve el autor actualizado
     */
    fun updateComplete(id: UUID,update: createAutorComplete)=
        repositorio.findById(id).map { autor->
            autor.nombre=update.nombre
            autor.Biografia=update.biografia
            autor.nacimiento= LocalDate.parse(update.nacimiento, DateTimeFormatter.ofPattern("d/MM/yyyy"))
            ResponseEntity.ok(repositorio.save(autor).toDetail())
        }.orElseThrow { AutorNotExist(id) }

    /**
     * Da de baja un autor, sus libros y elimina todos los me gustas y ejemplares subidos
     * @param id Identificador del autor a eliminar
     * @throws AutorNotExist El autor no existe
     */
    fun deleteAutor(id: UUID) : ResponseEntity<Any>{
        if (repositorio.existsById(id))
            repositorio.findById(id).map { autor->
                autor.alta=false
                autor.likeAutorUsuario.map {
                    it.removeAutorMeGusta(autor)
                }
                repositorio.save(autor)
                autor.libros.map { libro->
                    libro.alta=false
                    libroRepository.save(libro)
                    libro.likeLibroUsuario.map { like->
                        like.removeLibroMeGusta(libro)
                        userRepository.save(like)
                    }
                    libro.libroUsuario.map {
                        usuarioLibro.deleteById(it.id)
                    }
                }
            }.orElseThrow { AutorNotExist(id) }
        return ResponseEntity.noContent().build()
    }
    fun findByNombre(nombre:String) = repositorio.findByNombreIgnoreCase(nombre).map { it.toDetail() }.orElseThrow { AutorsNotExists() }
}
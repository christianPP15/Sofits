package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.Modelos.*
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.hibernate.type.EntityType

import org.springframework.data.domain.Page

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*

/**
 * Clase estereotipada como servicio que se encarga de la gestión de los libros y que extiende de BaseService
 * @author christianPP15
 * @see BaseService
 * @see Service
 */
@Service
class LibroService(
    /**
     * Repositorio de los autores
     * @see AutorRepository
     */
    val autorRepository: AutorRepository,
    /**
     * Repositorio de los ejemplares de los usuarios
     * @see UsuarioTieneLibroRepository
     */
    val usuarioLibro: UsuarioTieneLibroRepository,
    /**
     * Repositorio de los usuarios
     * @see UsuarioRepository
     */
    val userRepository:UsuarioRepository) : BaseService<Libro, UUID, LibroRepository>(){
    /**
     * Función para obtener todos los libros que están dados de alta en este momento de forma paginada
     * @param pageable Paginación de los resultados
     * @throws LibrosNotExists Mensaje para cuando no existen libros
     * @return Page con todos los libros dados de alta
     */
    fun getAllLibros(pageable: Pageable) = repositorio.obtenerLibrosDadosDeAlta(pageable).map { it.toDetailLibro() }.takeIf { !it.isEmpty } ?: throw LibrosNotExists()

    /**
     * Obtiene todos los detalles de un libro en base a su identificador
     * @param id Identificador del libro a consultar
     * @throws LibroNotExist En el caso de que el libro que se solicita no exista
     * @return Devuelve el detalle del libro que se solicita
     */
    fun getById(id:UUID) = repositorio.findById(id).map { it.toDtoAutor() }.orElseThrow { LibroNotExist(id) }

    /**
     * Agregamos un nuevo libro a un autor
     * @param id Identificador del autor al que pertenece el libro
     * @param create Información del libro que se esta intentando insertar
     * @throws AutorNotExist Si el autor que se indica que el libro no existe
     * @return Devuelve la entidad que se ha creado
     */
    fun addLibro(id: UUID,create: createLibro): LibroDetail {
        val autor = autorRepository.findById(id).orElseThrow { AutorNotExist(id) }
        val libro=Libro(create.titulo,create.descripcion)
        autor.libros.add(libro)
        libro.autor=autor
        autorRepository.save(autor)
        repositorio.save(libro)
        return libro.toDetailLibro()
    }

    /**
     * Función para editar el libro en base a su id
     * @param id Identificador del libro a editar
     * @param create Nuevos datos del libro
     * @return Devuelve el libro con la información editada
     * @throws AutorNotExist Lanza una exepción si el autor que se solicita no existe
     */
    fun editLibro(id: UUID,create: createLibro) = repositorio.findById(id).map { libro->
            libro.titulo=create.titulo
            libro.descripcion=create.descripcion
            repositorio.save(libro).toDetailLibro()
        }.orElseThrow { AutorNotExist(id) }

    /**
     * Da de baja un libro eliminando todas los ejemplares publicados sobre este y eliminando todos los me gustas de los usuarios
     * @param id Identificador del libro que deseamos dar de baja
     */
    fun removeLibro(id: UUID) {
        val libro= repositorio.findById(id).orElseThrow { LibroNotExist(id) }
        libro.alta=false
        repositorio.save(libro)
        libro.likeLibroUsuario.map { like->
            like.removeLibroMeGusta(libro)
            userRepository.save(like)
        }
        libro.libroUsuario.map {
            usuarioLibro.deleteById(it.id)
        }
    }

    fun findByArgs(titulo:Optional<String>, autor:Optional<String>, genero: Optional<String>, pageable: Pageable): Page<Libro?> {

        val specTitulo : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (!titulo.isEmpty){
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")),"%" + titulo.get() + "%")
                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }

        val specAutor : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (!autor.isEmpty){
                    val libroConGenero : Join<Libro, Autor> = root.join("autor")
                    criteriaBuilder.like(criteriaBuilder.lower(libroConGenero.get("nombre")),"%" + autor.get() + "%")
                    //criteriaBuilder.like(criteriaBuilder.lower(root.get("autor.nombre")),"%" + autor.get() + "%")
                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }

       val specGenero : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (!genero.isEmpty){
                    val libroConGenero : Join<Libro, GeneroLiterario> = root.join("generos")
                    criteriaBuilder.like(criteriaBuilder.lower(libroConGenero.get("nombre")),"%" + genero.get() + "%")

                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }

        val consulta = specTitulo.and(specAutor).and(specGenero)
        return this.repositorio.findAll(consulta,pageable)
    }
}
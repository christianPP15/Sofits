package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.AutorNotExist
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.Modelos.AutorRepository
import com.sofits.proyectofinal.Modelos.GeneroLiterario
import com.sofits.proyectofinal.Modelos.Libro
import com.sofits.proyectofinal.Modelos.LibroRepository
import com.sofits.proyectofinal.Servicios.base.BaseService

import org.springframework.data.domain.Page

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class LibroService(val autorRepository: AutorRepository) : BaseService<Libro, UUID, LibroRepository>(){

    fun getAllLibros(pageable: Pageable) = repositorio.findAll(pageable).map { it.toDetailLibro() }.takeIf { !it.isEmpty } ?: throw LibrosNotExists()

    fun getById(id:UUID) = repositorio.findById(id).map { it.toDtoAutor() }.orElseThrow { LibroNotExist(id) }

    fun addLibro(id: UUID,create: createLibro): LibroDetail {
        val autor = autorRepository.findById(id).orElseThrow { AutorNotExist(id) }
        val libro=Libro(create.titulo,create.descripcion)
        autor.libros.add(libro)
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

    fun findByArgs(titulo:Optional<String>, autor:Optional<String>, genero: Optional<String>, pageable: Pageable): Page<Libro?> {

        val specTitulo : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (titulo.isEmpty){
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")),"%" + titulo.get() + "%")
                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }

        val specAutor : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (autor.isEmpty){
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("autor.nombre")),"%" + autor.get() + "%")
                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }

       /* val specGenero : Specification<Libro> =
            Specification { root, query, criteriaBuilder ->
                if (genero.isEmpty){
                    val cq: CriteriaQuery<Libro> = criteriaBuilder.createQuery(Libro::class.java)
                    val libro: Root<Libro> = query.from(Libro::class.java)
                    val generos: ListJoin<Libro, GeneroLiterario> = libro.join(Libro::generos)
                    cq.select(libro)
                            .where(criteriaBuilder.equal(generos.get(GeneroLiterario::nombre),genero.get()))

                    //criteriaBuilder.like(criteriaBuilder.lower(root.get("generos.nombre")),"%" + genero.get() + "%")
                }else{
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                }
            }*/

        val consulta = specTitulo.and(specAutor)
        return this.repositorio.findAll(consulta,pageable)
    }
}
package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Autor (var nombre:String,
             var Biografia:String? = null,
             var imagen:String? = null,
             var nacimiento:LocalDate? = null,
             @OneToMany(mappedBy = "autor",cascade = arrayOf(CascadeType.ALL),orphanRemoval = true) val libros: MutableSet<Libro> = mutableSetOf(),
             @ManyToMany(mappedBy = "likeUsuarioAutor",fetch = FetchType.EAGER)
             var likeAutorUsuario:MutableSet<Usuario> = mutableSetOf(),
             @Id @GeneratedValue val id:UUID?=null){
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is Autor)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }
}

interface AutorRepository : JpaRepository<Autor,UUID>{

    fun findByNombreIgnoreCase (nombre: String) : Optional<Autor>
}
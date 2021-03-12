package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Autor (var nombre:String,
             var Biografia:String,
             var imagen:String,
             var nacimiento:LocalDate,
             @OneToMany(mappedBy = "autor") val libro: MutableSet<Libro> = mutableSetOf(),
             @ManyToMany(mappedBy = "likeUsuarioAutor",fetch = FetchType.EAGER)
             var likeAutorUsuario:MutableSet<Usuario> = mutableSetOf(),
             @Id @GeneratedValue val id:UUID){
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

interface AutorRepository : JpaRepository<Autor,UUID>
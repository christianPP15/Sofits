package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.*

@Entity
class Libro(var titulo:String,
            @Lob var descripcion:String,
            var estado:String,
            var portada:String,
            var idioma:String,
            var edicion:Int,
            var intercambiado:Boolean=false,
            @ManyToOne var autor: Autor?=null,
            @OneToMany(mappedBy = "libroUsuario",fetch = FetchType.EAGER) var libroUsuario: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
            @ManyToMany(mappedBy = "likeUsuarioLibro",fetch = FetchType.EAGER)
            var likeLibroUsuario:MutableSet<Usuario> = mutableSetOf(),
            @OneToMany val generos: MutableSet<GeneroLiterario> = mutableSetOf<GeneroLiterario>(),
            @Id @GeneratedValue val id:UUID?=null){

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is Libro)
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


interface LibroRepository: JpaRepository<Libro,UUID>
package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.*

@Entity
class Libro(@ApiModelProperty(value = "Título del libro",dataType = "String",position = 2,example = "Título del libro")
            var titulo:String,
            @ApiModelProperty(value = "Descripción del libro",dataType = "String",position = 3,example = "Descripción del libro")
            @Lob var descripcion:String?,
            @ApiModelProperty(value = "Autor que escribió el libro",dataType = "Autor",position = 4)
            @ManyToOne var autor: Autor?=null,
            @ApiModelProperty(value = "Copia del usuario",dataType = "UsuarioTieneLibro",position = 5)
            @OneToMany(mappedBy = "libroUsuario",fetch = FetchType.EAGER)
            var libroUsuario: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
            @ApiModelProperty(value = "Me gusta de los usuarios",dataType = "Usuario",position = 6)
            @ManyToMany(mappedBy = "likeUsuarioLibro",fetch = FetchType.EAGER)
            var likeLibroUsuario:MutableSet<Usuario> = mutableSetOf(),
            @ApiModelProperty(value = "Géneros de los libros",dataType = "GeneroLiterario",position = 7)
            @OneToMany val generos: MutableSet<GeneroLiterario> = mutableSetOf<GeneroLiterario>(),
            @ApiModelProperty(value = "Identificador del libro",dataType = "UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
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
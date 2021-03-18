package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Libro(@ApiModelProperty(value = "Título del libro",dataType = "java.lang.String",position = 2,example = "Título del libro")
            var titulo:String,
            @ApiModelProperty(value = "Descripción del libro",dataType = "java.lang.String",position = 3,example = "Descripción del libro")
            @Lob var descripcion:String?,
            @ApiModelProperty(value = "Autor que escribió el libro",dataType = "Autor",position = 4)
            @ManyToOne var autor: Autor?=null,
            @ApiModelProperty(value = "Atributo que indica si el libro esta dado de alta",dataType = "Boolean",position = 8)
            var alta:Boolean=true,
            @ApiModelProperty(value = "Copia del usuario",dataType = "UsuarioTieneLibro",position = 5)
            @OneToMany(mappedBy = "libroUsuario",fetch = FetchType.EAGER)
            var libroUsuario: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
            @ApiModelProperty(value = "Me gusta de los usuarios",dataType = "Usuario",position = 6)
            @ManyToMany(mappedBy = "likeUsuarioLibro",fetch = FetchType.EAGER)
            var likeLibroUsuario:MutableSet<Usuario> = mutableSetOf(),
            @ApiModelProperty(value = "Géneros de los libros",dataType = "GeneroLiterario",position = 7)
            @ManyToMany(fetch = FetchType.EAGER,targetEntity = GeneroLiterario::class)
            @JoinTable(
                    joinColumns = [JoinColumn(name = "libro_id")],
                    inverseJoinColumns = [JoinColumn(name = "genero_id")]
            )
            val generos: MutableSet<GeneroLiterario> = mutableSetOf<GeneroLiterario>(),
            @ApiModelProperty(value = "Identificador del libro",dataType = "java.util.UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
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


interface LibroRepository: JpaRepository<Libro,UUID> , JpaSpecificationExecutor<Libro?>{

    @Query("select e from Libro e where e.alta=true")
    fun obtenerLibrosDadosDeAlta(pageable: Pageable) : Page<Libro>

}
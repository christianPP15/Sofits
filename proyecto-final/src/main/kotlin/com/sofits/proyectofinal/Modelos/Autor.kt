package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Autor (@ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
             var nombre:String,
             @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
             var Biografia:String? = null,
             @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
             var nacimiento:LocalDate? = null,
             @ApiModelProperty(value = "Imagen del autor que ayude a reconocerlo",dataType = "Imagenes",position = 5)
             @OneToOne(cascade = [CascadeType.ALL])
             var imagen:Imagenes? = null,
             @ApiModelProperty(value = "Libros escritos por el autor",dataType = "Libro",position = 6)
             @OneToMany(mappedBy = "autor",cascade = [CascadeType.ALL],orphanRemoval = true,fetch = FetchType.EAGER)
             val libros: MutableSet<Libro> = mutableSetOf(),
             @ApiModelProperty(value = "Me gustas recibidos por los usuarios",dataType = "Usuario",position = 7)
             @ManyToMany(mappedBy = "likeUsuarioAutor",fetch = FetchType.EAGER)
             var likeAutorUsuario:MutableSet<Usuario> = mutableSetOf(),
             @ApiModelProperty(value = "Identificador de la entidad",dataType = "java.util.UUID", position = 1 ,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
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
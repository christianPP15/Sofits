package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 *  Clase que actua como entidad y que guarda toda la inforamción de un género literario
 *  @author ChristianPP15
 *  @see Entity
 */
@Entity
class GeneroLiterario (/**
                        Atributo que almacena el nombre con el que referirnos al género literario
                        */@ApiModelProperty(value = "Nombre del género literario",dataType = "java.lang.String",position = 2,example = "Fantasía")
                       var nombre:String,
                       /**
                        * Atributo que almacena el identificador de la entidad
                        * @see UUID
                        */
                       @ApiModelProperty(value = "Identificador del género literario",dataType = "java.util.UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                       @Id @GeneratedValue val id:UUID?=null){
    /**
     * Método equals que establece que dos entidades son iguales si son libros y tienen el mismo identificador
     */
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is GeneroLiterario)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }
    /**
     * Método hashcode que establece el id como el hashcode
     */
    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }
}
/**
 * Interfaz que actua como repositorio que extiende de JpaRepository
 * @see JpaRepository
 */
interface GeneroLiterarioRepository : JpaRepository<GeneroLiterario,UUID>
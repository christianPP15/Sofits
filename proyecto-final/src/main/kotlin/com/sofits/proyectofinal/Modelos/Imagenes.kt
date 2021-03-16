package com.sofits.proyectofinal.Modelos

import com.sofits.proyectofinal.upload.ImgurImageAttribute
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Imagenes(@ApiModelProperty(value = "Datos para interactuar con la imagen",dataType = "ImgurImageAttribute",position = 2)
               var img: ImgurImageAttribute?=null,
               @ApiModelProperty(value = "Identificador de la imagen",dataType = "UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
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

interface ImagenesRepository : JpaRepository<Imagenes,UUID>
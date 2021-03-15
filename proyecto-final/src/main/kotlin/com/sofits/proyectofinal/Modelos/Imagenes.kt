package com.sofits.proyectofinal.Modelos

import com.sofits.proyectofinal.upload.ImgurImageAttribute
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Imagenes(var img: ImgurImageAttribute?=null,
                    @Id @GeneratedValue val id:UUID?=null)

interface ImagenesRepository : JpaRepository<Imagenes,UUID>
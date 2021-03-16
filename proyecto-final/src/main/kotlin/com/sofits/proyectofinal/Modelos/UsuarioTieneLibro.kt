package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
class UsuarioTieneLibroId(@ApiModelProperty(value = "Usuario al que pertenece el libro",dataType = "UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                          val usuario_id: UUID,
                          @ApiModelProperty(value = "Libro que esta subiendo el usuario",dataType = "UUID",position = 2,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                          val libro_id: UUID) : Serializable


@Entity
class UsuarioTieneLibro(@ApiModelProperty(value = "Identificador formado por el id del usuario que realiza la subida y el libro que sube",dataType = "UsuarioTieneLibroId",position = 1)
                        @EmbeddedId val id:UsuarioTieneLibroId,
                        @ApiModelProperty(value = "Usuario que sube el libro",dataType = "Usuario",position = 2)
                        @ManyToOne
                           @MapsId("usuario_id")
                           @JoinColumn(name = "UsuarioId")
                        var usuarioLibro:Usuario,
                        @ApiModelProperty(value = "Libro que publica",dataType = "Libro",position = 3)
                        @ManyToOne
                           @MapsId("libro_id")
                           @JoinColumn(name = "Libro_id")
                        var libroUsuario:Libro,
                        @ApiModelProperty(value = "Descripción otorgada por el usuario",dataType = "String",position = 4,example = "Descripción del usuario sobre el libro")
                        @Lob var DescripccionLibro:String,
                        @ApiModelProperty(value = "Estado en la que se encuentra el libro",dataType = "String",position = 5,example = "Perfecto estado")
                        var estado:String,
                        @ApiModelProperty(value = "Idioma del libro",dataType = "String",position = 6,example = "Español")
                        var idioma:String,
                        @ApiModelProperty(value = "Edición del libro",dataType = "Int",position = 7,example = "3")
                        var edicion:Int,
                        @ApiModelProperty(value = "Indicador de si el libro ya ha sido transferido o no",dataType = "Boolean",position = 8,example = "false")
                        var intercambiado:Boolean=false,
                        @ApiModelProperty(value = "Imagen del libro subida por el usuario",dataType = "Imagenes",position = 9)
                        @OneToOne(cascade = [CascadeType.ALL]) var imagen: Imagenes? = null
)


interface UsuarioTieneLibroRepository: JpaRepository<UsuarioTieneLibro,UsuarioTieneLibroId>{

    @Query("select e from UsuarioTieneLibro e where usuarioLibro = :USUARIO")
    fun getAllBooksFromUser(pageable: Pageable,@Param("USUARIO") usuario:Usuario) : Page<UsuarioTieneLibro>

    @Query("select e from UsuarioTieneLibro e where e.libroUsuario= :LIBRO and e.intercambiado = false")
    fun getAllBooksEquals( pageable: Pageable,@Param("LIBRO") libro:Libro): Page<UsuarioTieneLibro>
}
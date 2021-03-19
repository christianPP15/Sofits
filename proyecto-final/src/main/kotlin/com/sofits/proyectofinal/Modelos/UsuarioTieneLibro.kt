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
/**
 * Clase que actua como id para las libros subidos por el usuario
 * @author christianPP15
 * @see Embeddable
 */
@Embeddable
class UsuarioTieneLibroId(
    /**
     * Identificador del usuario que sube el libro
     */
    @ApiModelProperty(value = "Usuario al que pertenece el libro",dataType = "java.util.UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
    val usuario_id: UUID,
    /**
     * Identificador del libro que sube el usuario
     */
    @ApiModelProperty(value = "Libro que esta subiendo el usuario",dataType = "java.util.UUID",position = 2,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
    val libro_id: UUID) : Serializable

/**
 * Clase que actua como entidad y que almacena los libros subidos por el usuario
 * @author christianPP15
 * @see Entity
 */
@Entity
class UsuarioTieneLibro(
    /**
     * Identificador compuesto formado por el identificador del usuario y del libro
     */
    @ApiModelProperty(value = "Identificador formado por el id del usuario que realiza la subida y el libro que sube",dataType = "UsuarioTieneLibroId",position = 1)
    @EmbeddedId val id:UsuarioTieneLibroId,
    /**
     * Atributo que crea una relación con la clase Usuario y que almacena el usuario que sube el libro
     * @see Usuario
     */
    @ApiModelProperty(value = "Usuario que sube el libro",dataType = "Usuario",position = 2)
    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "UsuarioId")
    var usuarioLibro:Usuario,
    /**
     * Atributo que crea una relación con la clase Libro y que almacena el libro que sube el usuario
     * @see Libro
     */
    @ApiModelProperty(value = "Libro que publica",dataType = "Libro",position = 3)
    @ManyToOne
    @MapsId("libro_id")
    @JoinColumn(name = "Libro_id")
    var libroUsuario:Libro,
    /**
     * Atributo que guarda la descripción dada por el usuario del libro
     */
    @ApiModelProperty(value = "Descripción otorgada por el usuario",dataType = "java.lang.String",position = 4,example = "Descripción del usuario sobre el libro")
    @Lob var DescripccionLibro:String,
    /**
     * Atributo que almacena el estado de conservación del libro
     */
    @ApiModelProperty(value = "Estado en la que se encuentra el libro",dataType = "java.lang.String",position = 5,example = "Perfecto estado")
    var estado:String,
    /**
     * Atributo que almacena el idioma del libro
     */
    @ApiModelProperty(value = "Idioma del libro",dataType = "String",position = 6,example = "Español")
    var idioma:String,
    /**
     * Atributo que almacena la edición del libro que tiene el usuario
     */
    @ApiModelProperty(value = "Edición del libro",dataType = "Int",position = 7,example = "3")
    var edicion:Int,
    /**
     * Atributo que almacena si el libro ya ha sido intercambiado o no
     */
    @ApiModelProperty(value = "Indicador de si el libro ya ha sido transferido o no",dataType = "java.lang.Boolean",position = 8,example = "false")
    var intercambiado:Boolean=false,
    /**
     * Atributo que almacena la imagen que suba el usuario sobre el libro
     * @see Imagenes
     */
    @ApiModelProperty(value = "Imagen del libro subida por el usuario",dataType = "Imagenes",position = 9)
    @OneToOne(cascade = [CascadeType.ALL]) var imagen: Imagenes? = null
)

/**
 * Interfaz que actua como repositorio que extiende de JpaRepository
 * @see JpaRepository
 */
interface UsuarioTieneLibroRepository: JpaRepository<UsuarioTieneLibro,UsuarioTieneLibroId>{
    /**
     * Obtener todos los libros subidos por un usuario
     */
    @Query("select e from UsuarioTieneLibro e where usuarioLibro = :USUARIO")
    fun getAllBooksFromUser(
        /**
         * Atributo que permite paginar los resultados
         */
        pageable: Pageable,
        /**
         * Usuario sobre el que se va a consultar las publicaciones
         */
        @Param("USUARIO") usuario:Usuario) : Page<UsuarioTieneLibro>

    /**
     * Obtener todos los ejemplares de un libro
     */
    @Query("select e from UsuarioTieneLibro e where e.libroUsuario= :LIBRO and e.intercambiado = false")
    fun getAllBooksEquals(
        /**
         * Atributo que permite paginar los resultados
         */
        pageable: Pageable,
        /**
         * Libro a consultar los ejemplares
         */
        @Param("LIBRO") libro:Libro): Page<UsuarioTieneLibro>
}
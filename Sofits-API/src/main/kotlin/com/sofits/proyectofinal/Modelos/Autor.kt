package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 *  Clase que actua como entidad y que guarda toda la inforamción de un autor
 *  @author ChristianPP15
 *  @see Entity
 */
@Entity
class Autor (
            /**
            * Atributo que almacena el nombre del autor
            */
            @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
             var nombre:String,
             /**
              * Atributo que almacena la biografía del autor
              */
             @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
             @Lob var Biografia:String? = null,
             /**
              * Atributo que almacena la fecha de nacimiento del autor
              * @see LocalDate
              */
             @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
             var nacimiento:LocalDate? = null,
             /**
              * Atributo que almacena y establece una relación con la entidad Imagenes para poder almacenar una imagen para el autor
              * @see Imagenes
              */
             @ApiModelProperty(value = "Imagen del autor que ayude a reconocerlo",dataType = "Imagenes",position = 5)
             @OneToOne(cascade = [CascadeType.ALL])
             var imagen:Imagenes? = null,
            /**
             * Atributo que almacena si el autor está activo o no
             */
            @ApiModelProperty(value = "Atributo que indica si un autor está o no activo",dataType = "Boolean",position = 8)
             var alta:Boolean = true,
            /**
             * Atributo que almacena y establece una relación con la entidad libros de forma que almacena los libros que ha escrito el autor
             * @see Libro
             */
             @ApiModelProperty(value = "Libros escritos por el autor",dataType = "Libro",position = 6)
             @OneToMany(mappedBy = "autor")
             val libros: MutableSet<Libro> = mutableSetOf(),
             /**
              * Atributo que almacena y establece una relación con la entidad usuarios ya que almacena los usuarios que han indicado que le gusta el autor
              * @see Usuario
              */
             @ApiModelProperty(value = "Me gustas recibidos por los usuarios",dataType = "Usuario",position = 7)
             @ManyToMany(mappedBy = "likeUsuarioAutor",fetch = FetchType.EAGER)
             var likeAutorUsuario:MutableSet<Usuario> = mutableSetOf(),
             /**
              * Atributo que almacena el identificador de la entidad
              * @see UUID
              */
             @ApiModelProperty(value = "Identificador de la entidad",dataType = "java.util.UUID", position = 1 ,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
             @Id @GeneratedValue val id:UUID?=null){
    /**
     * Método equals que establece que dos entidades son iguales si son autores y tienen el mismo identificador
     */
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is Autor)
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

    /**
     * Función auxiliar para agregar un libro a un editor y esblecer el autor de un libro
     */
    fun addLibro(libro: Libro){
        libros.add(libro)
        libro.autor=this
    }
    /**
     * Función auxiliar para eliminar un libro a un editor y esblecer el autor de un libro a nulo
     */
    fun removeLibro(libro: Libro){
        libros.remove(libro)
        libro.autor=null
    }
}

/**
 * Interfaz que actua como repositorio que extienda de JpaRepository
 * @see JpaRepository
 */
interface AutorRepository : JpaRepository<Autor,UUID>{

    @Query("select e from Autor e where e.alta=true")
    fun obtenerAutoresActivos(pageable: Pageable) : Page<Autor>

    fun findByNombreIgnoreCase (nombre: String) : Optional<Autor>
}
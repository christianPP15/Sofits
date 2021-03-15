package com.sofits.proyectofinal.Modelos

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
class UsuarioTieneLibroId(val usuario_id: UUID, val libro_id: UUID) : Serializable


@Entity
class UsuarioTieneLibro(@EmbeddedId val id:UsuarioTieneLibroId,
                        @ManyToOne
                           @MapsId("usuario_id")
                           @JoinColumn(name = "UsuarioId")
                        var usuarioLibro:Usuario,
                        @ManyToOne
                           @MapsId("libro_id")
                           @JoinColumn(name = "Libro_id")
                        var libroUsuario:Libro,
                        @Lob var DescripccionLibro:String,
                        var estado:String,
                        var idioma:String,
                        var edicion:Int,
                        var intercambiado:Boolean=false,
                        @OneToOne(cascade = [CascadeType.ALL]) var imagen: Imagenes? = null
)


interface UsuarioTieneLibroRepository: JpaRepository<UsuarioTieneLibro,UsuarioTieneLibroId>{

    @Query("select e from UsuarioTieneLibro e where usuarioLibro = :USUARIO")
    fun getAllBooksFromUser(pageable: Pageable,@Param("USUARIO") usuario:Usuario) : Page<UsuarioTieneLibro>

    @Query("select e from UsuarioTieneLibro e where e.libroUsuario= :LIBRO and e.intercambiado = false")
    fun getAllBooksEquals( pageable: Pageable,@Param("LIBRO") libro:Libro): Page<UsuarioTieneLibro>
}
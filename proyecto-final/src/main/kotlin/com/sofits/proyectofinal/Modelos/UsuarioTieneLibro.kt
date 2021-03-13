package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
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
                            var portada:String,
                            var idioma:String,
                            var edicion:Int,
                            var intercambiado:Boolean=false,)


interface UsuarioTieneLibroRepository: JpaRepository<UsuarioTieneLibro,UsuarioTieneLibroId>
package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
class ValoracionesUsuariosId(val usuarioValorado_id: UUID,val usuarioValorando_id:UUID) : Serializable

@Entity
class ValoracionesUsuarios(@EmbeddedId val id:ValoracionesUsuariosId,
                            @ManyToOne
                            @MapsId("usuarioValorado_id")
                            @JoinColumn(name = "UsuarioValoradoId")
                            var usuarioValorado:Usuario,
                           @ManyToOne
                           @MapsId("usuarioValorando_id")
                           @JoinColumn(name = "usuarioAValorarId")
                           var usuarioAValorar:Usuario,
                            var nota:Int)


interface ValoracionesUsuariosRepository : JpaRepository<ValoracionesUsuarios,ValoracionesUsuariosId>
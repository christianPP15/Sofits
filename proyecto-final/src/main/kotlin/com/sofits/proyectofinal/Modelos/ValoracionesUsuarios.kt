package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
class ValoracionesUsuariosId(@ApiModelProperty(value = "Identificador del usuario valorando",dataType = "UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                             val usuarioValorado_id: UUID,
                             @ApiModelProperty(value = "Identificador del usuario a valorar",dataType = "UUID",position = 2,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                             val usuarioValorando_id:UUID) : Serializable

@Entity
class ValoracionesUsuarios(@ApiModelProperty(value = "Identificador compuesto formado por los id de los usuarios",dataType = "ValoracionesUsuariosId",position = 1)
                           @EmbeddedId val id:ValoracionesUsuariosId,
                           @ApiModelProperty(value = "Usuario que esta llevando a cabo la valoración",dataType = "Usuario",position = 2)
                           @ManyToOne
                            @MapsId("usuarioValorado_id")
                            @JoinColumn(name = "UsuarioValoradoId")
                            var usuarioValorado:Usuario,
                           @ApiModelProperty(value = "Usuario que esta siendo valorado",dataType = "Usuario",position = 3)
                           @ManyToOne
                           @MapsId("usuarioValorando_id")
                           @JoinColumn(name = "usuarioAValorarId")
                           var usuarioAValorar:Usuario,
                           @ApiModelProperty(value = "Valoración dada por el usuario",dataType = "Int",position = 4,example = "3")
                           var nota:Int)


interface ValoracionesUsuariosRepository : JpaRepository<ValoracionesUsuarios,ValoracionesUsuariosId>{

    @Query("select e from ValoracionesUsuarios e where e.usuarioAValorar = :USUARIOVALORADO")
    fun getValorationFromOneUser(@Param("USUARIOVALORADO") usuarioValorado:Usuario) : List<ValoracionesUsuarios>

    @Query("select e from ValoracionesUsuarios e where e.usuarioValorado= :USUARIOVALORADO and e.usuarioAValorar= :USUARIOVALORANDO")
    fun userLikeAnotherUser(@Param("USUARIOVALORADO") usuarioValorado:Usuario,@Param("USUARIOVALORANDO") usuarioValorando: Usuario) : Optional<ValoracionesUsuarios>
}
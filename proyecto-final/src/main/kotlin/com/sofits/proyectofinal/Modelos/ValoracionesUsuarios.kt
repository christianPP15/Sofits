package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * Clase que actua como id para las valoraciones entre usuarios
 * @author christianPP15
 * @see Embeddable
 */
@Embeddable
class ValoracionesUsuariosId(
                            /**
                            * Atributo que almacena el identificador del usuario valorado
                            */
                            @ApiModelProperty(value = "Identificador del usuario valorando",dataType = "java.util.UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                             val usuarioValorado_id: UUID,
                            /**
                             * Atributo que almacena el identificador del usuario a valorar
                             */
                             @ApiModelProperty(value = "Identificador del usuario a valorar",dataType = "java.util.UUID",position = 2,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
                             val usuarioValorando_id:UUID) : Serializable

/**
 * Clase que actua como entidad y que almacena las valoraciones de un usuario
 * @author christianPP15
 * @see Entity
 */
@Entity
class ValoracionesUsuarios(
                            /**
                            * Identificador formado por el identificador de ambos usuarios
                             * @see ValoracionesUsuariosId
                            */
                            @ApiModelProperty(value = "Identificador compuesto formado por los id de los usuarios",dataType = "ValoracionesUsuariosId",position = 1)
                            @EmbeddedId val id:ValoracionesUsuariosId,
                            /**
                             * Relación que se establece con la entidad usuario y que almacena que usuario está valorando
                             * @see Usuario
                             */
                            @ApiModelProperty(value = "Usuario que esta llevando a cabo la valoración",dataType = "Usuario",position = 2)
                            @ManyToOne
                            @MapsId("usuarioValorado_id")
                            @JoinColumn(name = "UsuarioValoradoId")
                            var usuarioValorado:Usuario,
                            /**
                             * Relación que se establece con la entidad usuario y que almacena que usuario está siendo valorado
                             * @see Usuario
                             */
                            @ApiModelProperty(value = "Usuario que esta siendo valorado",dataType = "Usuario",position = 3)
                            @ManyToOne
                            @MapsId("usuarioValorando_id")
                            @JoinColumn(name = "usuarioAValorarId")
                            var usuarioAValorar:Usuario,
                            /**
                             * Atributo que almacena la valoración otorgada por el usuario
                             */
                            @ApiModelProperty(value = "Valoración dada por el usuario",dataType = "java.lang.Int",position = 4,example = "3")
                            var nota:Int)

/**
 * Interfaz que actua como repositorio que extiende de JpaRepository
 * @see JpaRepository
 */
interface ValoracionesUsuariosRepository : JpaRepository<ValoracionesUsuarios,ValoracionesUsuariosId>{

    /**
     * Consulta para obtener las valoraciones que le han dado a un usuario
     */
    @Query("select e from ValoracionesUsuarios e where e.usuarioAValorar = :USUARIOVALORADO")
    fun getValorationFromOneUser(
        /**
         * Usuario sobre el que vamos a buscar las valoraciones
         */
        @Param("USUARIOVALORADO") usuarioValorado:Usuario) : List<ValoracionesUsuarios>

    /**
     * Consulta para ver si un usuario ha dado me gusta a otro usuario
     */
    @Query("select e from ValoracionesUsuarios e where e.usuarioValorado= :USUARIOVALORADO and e.usuarioAValorar= :USUARIOVALORANDO")
    fun userLikeAnotherUser(
        /**
         * Usuario sobre que vamos a comprobar si ha valorado al otro
         */
        @Param("USUARIOVALORADO") usuarioValorado:Usuario,
        /**
         * Usuario sobre el que vamos a comprobar si ha sido valorado
         */
        @Param("USUARIOVALORANDO") usuarioValorando: Usuario) : Optional<ValoracionesUsuarios>
}
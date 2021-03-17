package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * Clase que actua como entidad y que almacena los usuarios
 * @author christianPP15
 * @see Entity
 */
@Entity
class Usuario(
    /**
     * Atributo que almacena el email del usuario
     */
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 2,example = "example@gmail.com")
    @Column(unique = true)private var email: String,
    /**
     * Atributo que almacena la contraseña del usuario
     */
    @ApiModelProperty(value = "Contraseña del usuario",dataType = "java.lang.String",position = 3,example = "password")
    private var password: String,
    /**
     * Atributo que almacena el nombre de usuario
     */
    @ApiModelProperty(value = "Nombre de usuario",dataType = "java.lang.String",position = 4,example = "Usuario ejemplo")
    var nombreUsuario: String,
    /**
     * Atributo que almacena la fecha de nacimiento del usuario
     * @see LocalDate
     */
    @ApiModelProperty(value = "Fecha de nacimiento del usuario",dataType = "java.util.LocalDate",position = 5,example = "15/04/2001")
    var fechaNacimiento:LocalDate,
    /**
     * Roles del usuario almacenados como un String usando el convertidor
     * @see RolesConverter
     */
    @ApiModelProperty(value = "Roles del usuario",dataType = "java.lang.String",position = 6,example = "['USER']")
    @Column
    @Convert(converter = RolesConverter::class)
    var roles: List<String>,
    /**
     * Atributo que almacena la imagen de perfil del usuario
     * @see Imagenes
     */
    @ApiModelProperty(value = "Imagen del usuario",dataType = "Imagenes",position = 7)
    @OneToOne(cascade = [CascadeType.ALL]) var imagen: Imagenes?,
    /**
     * Atributo que almacena la fecha de alta del usuario
     * @see LocalDate
     */
    @ApiModelProperty(value = "Fecha de creación del Usuario",dataType = "java.util.LocalDate",position = 8,example = "15/04/2001")
    var fechaAlta: LocalDate = LocalDate.now(),
    /**
     * Atributo que almacena si la cuenta a expirado
     */
    @ApiModelProperty(value = "Cuenta del usuario expirada",dataType = "java.lang.Boolean",position = 9,example = "true")
    private val nonExpired: Boolean = true,
    /**
     * Atributo que almacena si la cuenta se ha bloqueado
     */
    @ApiModelProperty(value = "Cuenta del usuario bloqueado",dataType = "java.lang.Boolean",position = 10,example = "false")
    private val nonLocked: Boolean = true,
    /**
     * Atributo que almacena si la cuenta esta activada
     */
    @ApiModelProperty(value = "Cuenta del usuario disponible",dataType = "java.lang.Boolean",position = 11,example = "true")
    private val enabled: Boolean = true,
    /**
     * Atributo que almacena si las creedenciales han expirado
     */
    @ApiModelProperty(value = "Credenciales del usuario activa",dataType = "java.lang.Boolean",position = 12,example = "false")
    private val credentialsNonExpired: Boolean = true,
    /**
     * Atributo que almacena si un usuario esta activo o no
     */
    @ApiModelProperty(value = "Usuario activo",dataType = "Boolean",position = 13,example = "true")
    var activo: Boolean =true,
    /**
     * Atributo que almacena las valoraciones de un Usuario
     * @see ValoracionesUsuarios
     */
    @ApiModelProperty(value = "Valoraciones dadas del usuario",dataType = "ValoracionesUsuarios",position = 14)
    @OneToMany(mappedBy = "usuarioValorado",fetch = FetchType.EAGER)
    var usuariosValorados: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    /**
     * Atributo que almacena las valoraciones recibidas de otros Usuarios
     * @see ValoracionesUsuarios
     */
    @ApiModelProperty(value = "Valoraciones recibidas del usuario",dataType = "ValoracionesUsuarios",position = 15)
    @OneToMany(mappedBy = "usuarioAValorar",fetch = FetchType.EAGER)
    var usuariosAValorar: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    /**
     * Atributo que almacena los libros que ha subido
     * @see UsuarioTieneLibro
     */
    @ApiModelProperty(value = "Libros subidos por el usuario",dataType = "UsuarioTieneLibro",position = 16)
    @OneToMany(mappedBy = "usuarioLibro",fetch = FetchType.EAGER)
    var usuarioLibro: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
    /**
     * Atributo que almacena los autores que gustan al usuario
     * @see Autor
     */
    @ApiModelProperty(value = "Autores que le gustan al usuario",dataType = "Autor",position = 17)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaAutor",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "autor_id")]
    )
    var likeUsuarioAutor:MutableSet<Autor> = mutableSetOf(),
    /**
     * Atributo que almacena los libros que gustan al usuario
     * @see Libro
     */
    @ApiModelProperty(value = "Libros que le gustan al usuario",dataType = "Libro",position = 18)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaLibro",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "libro_id")]
    )
    var likeUsuarioLibro:MutableSet<Libro> = mutableSetOf(),
    /**
     * Atributo que almacena el identificador del usuario
     * @see UUID
     */
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
    @Id @GeneratedValue val id: UUID?=null
) : UserDetails {
    /**
     * Función que se usa para obtener los roles de un usuario
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    /**
     * Función para acceder al atributo enabled
     */
    override fun isEnabled() = enabled

    /**
     * Función para obtener el nombre de usuario en nuestro claso el email
     */
    override fun getUsername() = email

    /**
     * Función para ver si las creedenciales de un usuario han expirado
     */
    override fun isCredentialsNonExpired() = credentialsNonExpired

    /**
     * Función para obtener la contraseña de un usuario
     */
    override fun getPassword() = password

    /**
     * Función para saber si una cuenta a expirado
     */
    override fun isAccountNonExpired() = nonExpired

    /**
     * Función para saber si una cuenta a sido bloqueada
     */
    override fun isAccountNonLocked() = nonLocked

    /**
     * Método equals que establece que dos entidades son iguales si son libros y tienen el mismo identificador
     */
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is Usuario)
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
     * Método auxiliar para indicar que un autor le gusta a un usuario
     */
    fun addAutorMeGusta(autorAdd: Autor){
        likeUsuarioAutor.add(autorAdd)
        autorAdd.likeAutorUsuario.add(this)
    }
    /**
     * Método auxiliar para remover un me gusta a un autor de un usuario
     */
    fun removeAutorMeGusta(autorRemove: Autor){
        likeUsuarioAutor.remove(autorRemove)
        autorRemove.likeAutorUsuario.remove(this)
    }
    /**
     * Método auxiliar para indicar que un libro le gusta a un usuario
     */
    fun addLibroMeGusta(libro: Libro){
        likeUsuarioLibro.add(libro)
        libro.likeLibroUsuario.add(this)
    }
    /**
     * Método auxiliar para remover un libro de los me gusta de un usuario
     */
    fun removeLibroMeGusta(libro: Libro){
        likeUsuarioLibro.remove(libro)
        libro.likeLibroUsuario.remove(libro)
    }
}
/**
 * Interfaz que actua como repositorio que extiende de JpaRepository
 * @see JpaRepository
 */
interface UsuarioRepository : JpaRepository<Usuario,UUID>{
    /**
     * Consulta para obtener un usuario en base a su email
     */
    fun findByEmail(
        /**
         * Email del usuario a buscar
         */
        email : String) : Optional<Usuario>
}
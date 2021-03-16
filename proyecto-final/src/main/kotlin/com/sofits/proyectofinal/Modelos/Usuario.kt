package com.sofits.proyectofinal.Modelos

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Usuario(
    @ApiModelProperty(value = "Email del usuario",dataType = "String",position = 2,example = "example@gmail.com")
    @Column(unique = true)private var email: String,
    @ApiModelProperty(value = "Contraseña del usuario",dataType = "String",position = 3,example = "password")
    private var password: String,
    @ApiModelProperty(value = "Nombre de usuario",dataType = "String",position = 4,example = "Usuario ejemplo")
    var nombreUsuario: String,
    @ApiModelProperty(value = "Fecha de nacimiento del usuario",dataType = "LocalDate",position = 5,example = "15/04/2001")
    var fechaNacimiento:LocalDate,
    @ApiModelProperty(value = "Roles del usuario",dataType = "String",position = 6,example = "['USER']")
    @Column
    @Convert(converter = RolesConverter::class)
    var roles: List<String>,
    @ApiModelProperty(value = "Imagen del usuario",dataType = "Imagenes",position = 7)
    @OneToOne(cascade = [CascadeType.ALL]) var imagen: Imagenes?,
    @ApiModelProperty(value = "Fecha de creación del Usuario",dataType = "LocalDate",position = 8,example = "15/04/2001")
    var fechaAlta: LocalDate = LocalDate.now(),
    @ApiModelProperty(value = "Cuenta del usuario expirada",dataType = "Boolean",position = 9,example = "true")
    private val nonExpired: Boolean = true,
    @ApiModelProperty(value = "Cuenta del usuario bloqueado",dataType = "Boolean",position = 10,example = "false")
    private val nonLocked: Boolean = true,
    @ApiModelProperty(value = "Cuenta del usuario disponible",dataType = "Boolean",position = 11,example = "true")
    private val enabled: Boolean = true,
    @ApiModelProperty(value = "Credenciales del usuario activa",dataType = "Boolean",position = 12,example = "false")
    private val credentialsNonExpired: Boolean = true,
    @ApiModelProperty(value = "Usuario activo",dataType = "Boolean",position = 13,example = "true")
    var activo: Boolean =true,
    @ApiModelProperty(value = "Valoraciones dadas del usuario",dataType = "ValoracionesUsuarios",position = 14)
    @OneToMany(mappedBy = "usuarioValorado",fetch = FetchType.EAGER)
    var usuariosValorados: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    @ApiModelProperty(value = "Valoraciones recibidas del usuario",dataType = "ValoracionesUsuarios",position = 15)
    @OneToMany(mappedBy = "usuarioAValorar",fetch = FetchType.EAGER)
    var usuariosAValorar: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    @ApiModelProperty(value = "Libros subidos por el usuario",dataType = "UsuarioTieneLibro",position = 16)
    @OneToMany(mappedBy = "usuarioLibro",fetch = FetchType.EAGER)
    var usuarioLibro: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
    @ApiModelProperty(value = "Autores que le gustan al usuario",dataType = "Autor",position = 17)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaAutor",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "autor_id")]
    )
    var likeUsuarioAutor:MutableSet<Autor> = mutableSetOf(),
    @ApiModelProperty(value = "Libros que le gustan al usuario",dataType = "Libro",position = 18)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaLibro",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "libro_id")]
    )
    var likeUsuarioLibro:MutableSet<Libro> = mutableSetOf(),
    @ApiModelProperty(value = "Identificador del usuario",dataType = "UUID",position = 1,example = "91aeceab-6f89-4fec-a6ff-4674ed2e7604")
    @Id @GeneratedValue val id: UUID?=null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun isEnabled() = enabled
    override fun getUsername() = email
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is Usuario)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }
    fun addAutorMeGusta(autorAdd: Autor){
        likeUsuarioAutor.add(autorAdd)
        autorAdd.likeAutorUsuario.add(this)
    }
    fun removeAutorMeGusta(autorRemove: Autor){
        likeUsuarioAutor.remove(autorRemove)
        autorRemove.likeAutorUsuario.remove(this)
    }

    fun addLibroMeGusta(libro: Libro){
        likeUsuarioLibro.add(libro)
        libro.likeLibroUsuario.add(this)
    }
    fun removeLibroMeGusta(libro: Libro){
        likeUsuarioLibro.remove(libro)
        libro.likeLibroUsuario.remove(libro)
    }
}

interface UsuarioRepository : JpaRepository<Usuario,UUID>{
    fun findByEmail(email : String) : Optional<Usuario>
}
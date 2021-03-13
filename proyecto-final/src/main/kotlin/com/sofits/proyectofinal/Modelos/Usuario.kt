package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Usuario(
    @Column(unique = true)private var email: String,
    private var password: String,
    var nombreUsuario: String,
    var fechaNacimiento:LocalDate,
    @Column
    @Convert(converter = RolesConverter::class)
    var roles: List<String>,
    var fechaAlta: LocalDate = LocalDate.now(),
    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    private val enabled: Boolean = true,
    private val credentialsNonExpired: Boolean = true,
    var activo: Boolean =true,
    @OneToMany(mappedBy = "usuarioValorado",fetch = FetchType.EAGER) var usuariosValorados: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    @OneToMany(mappedBy = "usuarioAValorar",fetch = FetchType.EAGER) var usuariosAValorar: MutableSet<ValoracionesUsuarios> = mutableSetOf(),
    @OneToMany(mappedBy = "usuarioLibro",fetch = FetchType.EAGER) var usuarioLibro: MutableSet<UsuarioTieneLibro> = mutableSetOf(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaAutor",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "autor_id")]
    )
    var likeUsuarioAutor:MutableSet<Autor> = mutableSetOf(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MeGustaLibro",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "libro_id")]
    )
    var likeUsuarioLibro:MutableSet<Libro> = mutableSetOf(),
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
    fun eliminarAutorMeGusta(autorRemove: Autor){
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
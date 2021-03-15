package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UserService(
    private val repo: UsuarioRepository,
    private val encoder: PasswordEncoder,
    private val libroService: LibroService,
    private val autorService: AutorService
) {

    fun create(newUser : CreateUserDTO): Optional<Usuario> {
        if (findByEmail(newUser.email).isPresent)
            return Optional.empty()
        return Optional.of(
            with(newUser) {
                repo.save(Usuario(email, encoder.encode(password), nombre, LocalDate.parse(newUser.fechaNacimiento,
                    DateTimeFormatter.ofPattern("d/MM/yyyy")) ,listOf("USER"),null))
            }

        )
    }

    fun findByEmail(email : String) = repo.findByEmail(email)

    fun findById(id : UUID) = repo.findById(id)

    fun addMeGustaLibro(user:Usuario,id: UUID) : ResponseEntity<Any>{
        val libro=libroService.findById(id).orElseThrow { LibroNotExist(id) }
        if (!user.likeUsuarioLibro.contains(libro))
            user.addLibroMeGusta(libro)
        repo.save(user)
        libroService.save(libro)
        return ResponseEntity.noContent().build()
    }

    fun removeMeGustaLibro(user:Usuario,id: UUID) : ResponseEntity<Any>{
        val libro=libroService.findById(id).orElseThrow { LibroNotExist(id) }
        if (user.likeUsuarioLibro.contains(libro))
            user.removeLibroMeGusta(libro)
        repo.save(user)
        libroService.save(libro)
        return ResponseEntity.noContent().build()
    }

    fun addMeGustaAutor(user:Usuario,id: UUID) : ResponseEntity<Any>{
        val autor=autorService.findById(id).orElseThrow { LibroNotExist(id) }
        if (!user.likeUsuarioAutor.contains(autor))
            user.addAutorMeGusta(autor)
        repo.save(user)
        autorService.save(autor)
        return ResponseEntity.noContent().build()
    }
    fun removeMeGustaAutor(user:Usuario,id: UUID) : ResponseEntity<Any>{
        val autor=autorService.findById(id).orElseThrow { LibroNotExist(id) }
        if (user.likeUsuarioAutor.contains(autor))
            user.removeAutorMeGusta(autor)
        repo.save(user)
        autorService.save(autor)
        return ResponseEntity.noContent().build()
    }

    fun misAutoresFavoritos(user: Usuario) = ResponseEntity.ok().body(user.likeUsuarioAutor.map { it.toCrateDto() })

    fun misLibrosFavoritos(user: Usuario) = ResponseEntity.ok().body(user.likeUsuarioLibro.map { it.toDetailLibro() })
}
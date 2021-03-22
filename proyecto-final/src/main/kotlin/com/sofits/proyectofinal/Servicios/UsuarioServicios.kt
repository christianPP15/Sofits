package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Clase estereotipada como servicio que se encarga de la gestión de los autores y que extiende de BaseService
 * @author christianPP15
 * @see BaseService
 * @see Service
 */
@Service
class UserService(
    /**
     * Repositorio que gestiona los usuarios
     */
    private val repo: UsuarioRepository,
    /**
     * Encriptador de contraseñas en Bcrypt
     */
    private val encoder: PasswordEncoder,
    /**
     * Servicio encargado de la gestión de los libros
     */
    private val libroService: LibroService,
    /**
     * Servicio encargado de la gestión de los autores
     */
    private val autorService: AutorService
) {
    /**
     * Función para crear un nuevo usuario
     * @param newUser Datos del nuevo usuario que se quiere registrar en la web
     * @return Devuelve un optional vacío o bien un optional con los datos del nuevo usuario
     */
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

    /**
     * Función para encontrar a un usuario en base a su email
     * @param email Email del usuario a buscar
     * @return Usuario encontrado
     */
    fun findByEmail(email : String) = repo.findByEmail(email)

    /**
     * Función para encontrar a un usuario en base a su id
     * @param id Identificador del usuario a buscar
     * @return Usuario encontrado
     */
    fun findById(id : UUID) = repo.findById(id)

    /**
     * Función para agregar un me gusta a un libro
     * @param user Usuario al que agregar el me gusta
     * @param id Identificador del libro al que indicar el me gusta
     */
    fun addMeGustaLibro(user:Usuario,id: UUID) {
        val libro=libroService.findById(id).orElseThrow { LibroNotExist(id) }
        if (!user.likeUsuarioLibro.contains(libro))
            user.addLibroMeGusta(libro)
        repo.save(user)
        libroService.save(libro).toDetailLibro()
    }

    /**
     * Función para eliminar un me gusta a un libro
     * @param user Usuario al que agregar el me gusta
     * @param id Identificador del libro al que indicar el me gusta
     */
    fun removeMeGustaLibro(user:Usuario,id: UUID){
        val libro=libroService.findById(id).orElseThrow { LibroNotExist(id) }
        if (user.likeUsuarioLibro.contains(libro))
            user.removeLibroMeGusta(libro)
        repo.save(user)
        libroService.save(libro)
    }
    /**
     * Función para agregar un me gusta a un autor
     * @param user Usuario al que agregar el me gusta
     * @param id Identificador del autor al que indicar el me gusta
     */
    fun addMeGustaAutor(user:Usuario,id: UUID): AutoresDetailMeGusta {
        val autor=autorService.findById(id).orElseThrow { LibroNotExist(id) }
        if (!user.likeUsuarioAutor.contains(autor))
            user.addAutorMeGusta(autor)
        repo.save(user)
        return autorService.save(autor).toDetailMeGusta(user)
    }
    /**
     * Función para agregar un me gusta a un autor
     * @param user Usuario al que agregar el me gusta
     * @param id Identificador del autor al que indicar el me gusta
     */
    fun removeMeGustaAutor(user:Usuario,id: UUID) {
        val autor=autorService.findById(id).orElseThrow { LibroNotExist(id) }
        if (user.likeUsuarioAutor.contains(autor))
            user.removeAutorMeGusta(autor)
        repo.save(user)
        autorService.save(autor)
    }

    /**
     * Función para obtener los autores favoritos de un usuario
     * @param user Usuario del que obtenemos sus autores favoritos
     * @return Lista con todos los autores favoritos de un usuario
     */
    fun misAutoresFavoritos(user: Usuario) = user.likeUsuarioAutor.map { it.toCrateDto() }

    /**
     * Función para obtener los libros favoritos de un usuario
     * @param user Usuario del que obtenemos sus libros favoritos
     * @return Lista con todos los libros favoritos de un usuario
     */
    fun misLibrosFavoritos(user: Usuario) = user.likeUsuarioLibro.map { it.toDetailLibro() }
}
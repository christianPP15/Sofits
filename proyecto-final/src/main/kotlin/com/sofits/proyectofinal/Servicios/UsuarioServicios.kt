package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.CreateUserDTO
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserService(
    private val repo: UsuarioRepository,
    private val encoder: PasswordEncoder
) {

    fun create(newUser : CreateUserDTO): Optional<Usuario> {
        if (findByEmail(newUser.email).isPresent)
            return Optional.empty()
        return Optional.of(
            with(newUser) {
                repo.save(Usuario(email, encoder.encode(password), fullName, LocalDate.parse(newUser.fechaNacimiento) ,listOf("USER")))
            }

        )
    }

    fun findByEmail(email : String) = repo.findByEmail(email)

    fun findById(id : UUID) = repo.findById(id)

}
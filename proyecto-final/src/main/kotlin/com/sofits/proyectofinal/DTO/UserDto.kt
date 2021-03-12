package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Usuario
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class UserDTOlogin(
    var email : String,
    var nombre: String,
    val id: UUID? = null
)
data class UserDTORegisterModel(
    val email: String,
    val nombre: String,
    val fechaNacimiento: LocalDate,
    val id: UUID? = null
)
data class UserDTO(
    var email : String,
    var fullName: String,
    var roles: String,
    val id: UUID? = null
)

fun Usuario.toUserDTO() = UserDTO(username, nombreCompleto, roles.joinToString(), id)


fun Usuario.UserDTOLogin() = UserDTOlogin(username,nombreCompleto, id)

fun Usuario.UserDTORegister() = UserDTORegisterModel(username,nombreCompleto,fechaNacimiento,id)

data class CreateUserDTO(
    @get:NotBlank(message = "{user.email.notBlank}")
    @get:Email(message = "{user.email.valid}")
    var email:String,
    @get:NotBlank(message = "{user.fullname.notBlank}")
    var fullName: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password2: String,
    @get:NotNull(message = "{user.nacimiento.notNull}")
    var fechaNacimiento: String
)
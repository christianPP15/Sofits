package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Imagenes
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class UserDTOlogin(
    var email : String,
    var nombre: String,
    var imagen: ImagenWithoutHash?,
    val id: UUID? = null
)
data class UserDTORegisterModel(
    val email: String,
    val nombre: String,
    val fechaNacimiento: LocalDate,
    val imagen: ImagenWithoutHash?,
    val id: UUID? = null
)
data class UserDTO(
    var email : String,
    var fullName: String,
    var roles: String,
    val id: UUID? = null
)
data class UserLibroDto(
    val id: UUID?,
    val nombre:String
)
fun Usuario.toDtoLibro() : UserLibroDto = UserLibroDto(id,nombreUsuario)

fun Usuario.toUserDTO() = UserDTO(username, nombreUsuario, roles.joinToString(), id)


fun Usuario.UserDTOLogin() = UserDTOlogin(username,nombreUsuario,imagen?.toDto(), id)

fun Usuario.UserDTORegister() = UserDTORegisterModel(username,nombreUsuario,fechaNacimiento,imagen?.toDto(),id)

data class CreateUserDTO(
    @get:NotBlank(message = "{user.email.notBlank}")
    @get:Email(message = "{user.email.valid}")
    var email:String,
    @get:NotBlank(message = "{user.fullname.notBlank}")
    var nombre: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password2: String,
    @get:NotNull(message = "{user.nacimiento.notNull}")
    var fechaNacimiento: String
)
data class JwtUserResponseLogin(
    val token: String,
    val refreshToken: String,
    val user: UserDTOlogin
)

data class JWTUserResponseRegister(
    val token: String,
    val refreshToken: String,
    val user: UserDTORegisterModel
)
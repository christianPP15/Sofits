package com.sofits.proyectofinal.DTO

import com.sofits.proyectofinal.Modelos.Imagenes
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class UserDTOlogin(
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 2)
    var email : String,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 3)
    var nombre: String,
    @ApiModelProperty(value = "Imagen de perfil del usuario",dataType = "ImagenWithoutHash",position = 4)
    var imagen: ImagenWithoutHash?,
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID? = null
)
data class UserDTORegisterModel(
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 2)
    val email: String,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 3)
    val nombre: String,
    @ApiModelProperty(value = "Fecha de nacimiento del usuario",dataType = "java.util.LocalDate",position = 4)
    val fechaNacimiento: LocalDate,
    @ApiModelProperty(value = "Imagen de perfil del usuario",dataType = "ImagenWithoutHash",position = 5)
    val imagen: ImagenWithoutHash?,
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID? = null
)
data class UserValoracionDto(
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 2)
    var email : String,
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID? = null)
data class UserDTO(
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 2)
    var email : String,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 3)
    var fullName: String,
    @ApiModelProperty(value = "Roles del usuario",dataType = "java.lang.String",position = 4)
    var roles: String,
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID? = null
)
data class UserLibroDto(
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID?,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 3)
    val nombre:String
)
fun Usuario.toValoracionDto() = UserValoracionDto(username,id)
fun Usuario.toDtoLibro() : UserLibroDto = UserLibroDto(id,nombreUsuario)

fun Usuario.toUserDTO() = UserDTO(username, nombreUsuario, roles.joinToString(), id)


fun Usuario.UserDTOLogin() = UserDTOlogin(username,nombreUsuario,imagen?.toDto(), id)

fun Usuario.UserDTORegister() = UserDTORegisterModel(username,nombreUsuario,fechaNacimiento,imagen?.toDto(),id)

data class CreateUserDTO(
    @ApiModelProperty(value = "Email del usuario",dataType = "java.lang.String",position = 1)
    @get:NotBlank(message = "{user.email.notBlank}")
    @get:Email(message = "{user.email.valid}")
    var email:String,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 2)
    @get:NotBlank(message = "{user.fullname.notBlank}")
    var nombre: String,
    @ApiModelProperty(value = "Contraseña del usuario",dataType = "java.lang.String",position = 3)
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    @ApiModelProperty(value = "Contraseña repetida del usuario",dataType = "java.lang.String",position = 4)
    val password2: String,
    @ApiModelProperty(value = "Fecha de nacimiento del usuario",dataType = "java.util.LocalDate",position = 5)
    @get:NotNull(message = "{user.nacimiento.notNull}")
    var fechaNacimiento: String
)
data class JwtUserResponseLogin(
    @ApiModelProperty(value = "Token de sesión del usuario",dataType = "java.lang.String",position = 1)
    val token: String,
    @ApiModelProperty(value = "Token de refresco del usuario, para generar un nuevo token en el caso de que el token caduque",dataType = "java.lang.String",position = 2)
    val refreshToken: String,
    @ApiModelProperty(value = "Información del usuario que inicia sesión",dataType = "UserDTOlogin",position = 3)
    val user: UserDTOlogin
)

data class JWTUserResponseRegister(
    @ApiModelProperty(value = "Token de sesión del usuario",dataType = "java.lang.String",position = 1)
    val token: String,
    @ApiModelProperty(value = "Token de refresco del usuario, para generar un nuevo token en el caso de que el token caduque",dataType = "java.lang.String",position = 2)
    val refreshToken: String,
    @ApiModelProperty(value = "Información del usuario que inicia sesión",dataType = "UserDTOlogin",position = 3)
    val user: UserDTORegisterModel
)
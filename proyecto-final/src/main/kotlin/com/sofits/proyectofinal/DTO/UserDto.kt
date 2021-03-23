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

/**
 * Data class que define el modelo de respuesta para el login
 * @property email Email del usuario logueado
 * @property nombre Nombre del usuario
 * @property imagen Imagen del usuario logueado
 * @property id Identificador del usuario logueado
 * @see ImagenWithoutHash
 */
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

/**
 * Data class que define el modelo de respuesta de registro
 * @property email Email del usuario logueado
 * @property nombre Nombre del usuario
 * @property fechaNacimiento Fecha de nacimiento del usuario
 * @property imagen Imagen del usuario que se registra
 * @property id Identificador del usuario registrado
 * @see ImagenWithoutHash
 */
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




/**
 * Data class para mostrar el nombre del usuario y su identificador
 * @property id Identificador del usuario
 * @property nombre Nombre de usuario
 */
data class UserLibroDto(
    @ApiModelProperty(value = "Identificador del usuario",dataType = "java.util.UUID",position = 1)
    val id: UUID?,
    @ApiModelProperty(value = "Nombre del usuario",dataType = "java.lang.String",position = 3)
    val nombre:String
)

/**
 * Función para convertir un usuario  en un UserLibroDto
 * @see UserLibroDto
 */
fun Usuario.toDtoLibro() : UserLibroDto = UserLibroDto(id,nombreUsuario)

/**
 * Función para convertir un usuario a UserDTOlogin
 * @see UserDTOlogin
 */
fun Usuario.UserDTOLogin() = UserDTOlogin(username,nombreUsuario,imagen?.toDto(), id)

/**
 * Función para convertir un usuario a UserDTORegisterModel
 * @see UserDTORegisterModel
 */
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

/**
 * Data class de devolución con la información del login
 * @property token Token jwt para la autenticación del usuario
 * @property refreshToken Token de refresco del usuario
 * @property user Información del usuario que realiza el login
 */
data class JwtUserResponseLogin(
    @ApiModelProperty(value = "Token de sesión del usuario",dataType = "java.lang.String",position = 1)
    val token: String,
    @ApiModelProperty(value = "Token de refresco del usuario, para generar un nuevo token en el caso de que el token caduque",dataType = "java.lang.String",position = 2)
    val refreshToken: String,
    @ApiModelProperty(value = "Información del usuario que inicia sesión",dataType = "UserDTOlogin",position = 3)
    val user: UserDTOlogin
)
/**
 * Data class de devolución con la información del registro
 * @property token Token jwt para la autenticación del usuario
 * @property refreshToken Token de refresco del usuario
 * @property user Información del usuario que realiza el registro
 */
data class JWTUserResponseRegister(
    @ApiModelProperty(value = "Token de sesión del usuario",dataType = "java.lang.String",position = 1)
    val token: String,
    @ApiModelProperty(value = "Token de refresco del usuario, para generar un nuevo token en el caso de que el token caduque",dataType = "java.lang.String",position = 2)
    val refreshToken: String,
    @ApiModelProperty(value = "Información del usuario que inicia sesión",dataType = "UserDTOlogin",position = 3)
    val user: UserDTORegisterModel
)
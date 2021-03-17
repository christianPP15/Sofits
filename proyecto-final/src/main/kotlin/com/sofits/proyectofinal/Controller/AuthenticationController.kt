package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.ApiError
import com.sofits.proyectofinal.ErrorControl.ApiSubError
import com.sofits.proyectofinal.ErrorControl.UserAlreadyExit
import com.sofits.proyectofinal.ErrorControl.UserNotFoundById
import com.sofits.proyectofinal.Modelos.ImagenesRepository
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioRepository
import com.sofits.proyectofinal.Seguridad.jwt.BearerTokenExtractor
import com.sofits.proyectofinal.Seguridad.jwt.JwtTokenProvider
import com.sofits.proyectofinal.Servicios.ImagenServicio
import com.sofits.proyectofinal.Servicios.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank


@RestController
class AuthenticationController() {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    lateinit var bearerTokenExtractor: BearerTokenExtractor
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var  servicioImagenes: ImagenServicio
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository


    @ApiOperation(value = "Obtener las credenciales de acceso e información del usuario",
        notes = "Mecanismo para generar un token de acceso y de refresco que permitirá realizar el resto de peticiones")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = UserDTOlogin::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiSubError::class)
    ])
    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody
              @ApiParam(value = "Objeto de tipo LoginRequest con el username y password del usuario para realizar el login",required = true,type = "LoginRequest")
              loginRequest : LoginRequest) : ResponseEntity<JwtUserResponseLogin> {
        val authentication = do_authenticate(loginRequest.username,loginRequest.password)

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as Usuario

        val jwtToken = jwtTokenProvider.generateToken(user)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponseLogin(jwtToken, jwtRefreshToken, user.UserDTOLogin()))

    }
    @PostMapping("/auth/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponseLogin> {

        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                val userId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val user: Usuario = userService.findById(userId).orElseThrow {
                    UserNotFoundById(userId)
                }
                val jwtToken = jwtTokenProvider.generateToken(user)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponseLogin(jwtToken, jwtRefreshToken, user.UserDTOLogin()))
            }
        } catch (ex : Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        return ResponseEntity.badRequest().build()

    }


    @ApiOperation(value = "Registrar al usuario en la web y obtener sus credenciales de acceso",
        notes = "Mecanismo para registrar al usuario en la aplicación y generar un token de acceso y de refresco que permitirá realizar el resto de peticiones")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = UserDTOlogin::class),
        ApiResponse(code = 401,message = "Unauthorized",response = ApiError::class),
        ApiResponse(code = 400,message = "Bad Request",response = ApiSubError::class)
    ])
    @PostMapping("/auth/register")
    fun nuevoUsuario(@Valid
                    @ApiParam(value = "Objeto con la información necesaria del usuario para llevar a cabo el registro", required = true,type = "CreateUserDTO")
                     @RequestPart("newUser") newUser : CreateUserDTO,
                     @ApiParam(value = "Imagen de perfil del usuario",required = false,type = "MultipartFile")
                     @RequestPart("file") file: MultipartFile): ResponseEntity<JWTUserResponseRegister> {
        var user=userService.create(newUser).orElseThrow { UserAlreadyExit(newUser.email) }
        val imagen = servicioImagenes.save(file)
        user.imagen=imagen
        usuarioRepository.save(user)
        val authentication=do_authenticate(newUser.email,newUser.password)
        SecurityContextHolder.getContext().authentication = authentication
        val user1 = authentication.principal as Usuario
        val jwtToken = jwtTokenProvider.generateToken(user1)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user1)
        return ResponseEntity.status(HttpStatus.CREATED).body(JWTUserResponseRegister(jwtToken, jwtRefreshToken, user.UserDTORegister()))
    }


    private fun do_authenticate(username: String,password: String): Authentication {
        return  authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username, password
            )
        )
    }
}


data class LoginRequest(
    @get:NotBlank(message = "{user.username.notBlank}") val username : String,
    @get:NotBlank(message = "{user.password.notBlank}") val password: String
)


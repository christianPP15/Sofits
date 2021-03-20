package com.sofits.proyectofinal.Seguridad.jwt

import com.sofits.proyectofinal.Modelos.Usuario
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Clase estereotipada como Componente y que se encarga de proveer un token
 * @author lmlopezmagana
 * @see Component
 */
@Component
class JwtTokenProvider(
    /**
     * Secreto que se introduce desde el fichero de properties
     */
    @Value("\${JWT.SECRET}") private val jwtSecreto : String,
    /**
     * Duración del token establecido en el fichero de properties
     */
    @Value("\${JWT.DURATION}") private val jwtDuracionToken : Long,
    /**
     * Duración del token de refresco en el fichero de properties
     */
    @Value("\${JWT.DURATION.REFRESH}") private val jwtDuracionRefreshToken : Long) {

    companion object {
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_TYPE = "JWT"
    }

    /**
     * Atributo que permite convertir el token del usuario
     */
    private val parser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.toByteArray())).build()

    /**
     * Crea un logger para mostrar información por la traza de la pila
     */
    private val logger: org.slf4j.Logger? = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    /**
     * Genera el token del usuario
     * @param user Usuario en base al cual generaremos el token
     * @param isRefreshToken Comprobamos si es o no el token de refresco
     */
    private fun generateTokens(user: Usuario, isRefreshToken: Boolean): String {
        val tokenExpirationDate =
            Date.from(
                Instant.now().plus(if (isRefreshToken) jwtDuracionRefreshToken else jwtDuracionToken, ChronoUnit.DAYS)
            )
        val builder = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecreto.toByteArray()), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setSubject(user.id.toString())
            .setExpiration(tokenExpirationDate)
            .setIssuedAt(Date())
            .claim("refresh", isRefreshToken)

        if (!isRefreshToken) {
            builder
                .claim("email", user.username)
                .claim("roles", user.roles.toString())

        }
        return builder.compact()
    }


    /**
     * Genera el token normal de un usuario
     * @param user al que generar el token
     */
    fun generateToken(user: Usuario) = generateTokens(user, false)

    /**
     * Genera el token de refresco de un usuario
     * @param user al que generar el token
     */
    fun generateRefreshToken(user: Usuario) = generateTokens(user, true)

    /**
     * Obtiene el identificador del usuario en base a su token
     * @param token Token del usuario del que extraer el id
     */
    fun getUserIdFromJWT(token: String): UUID = UUID.fromString(parser.parseClaimsJws(token).body.subject)

    /**
     * Valida el token de refresco de un usuario
     * @param token Token a validar
     */
    fun validateRefreshToken(token: String) = validateToken(token, true)

    /**
     * Valida el token de un usuario
     * @param token Token a validar
     */
    fun validateAuthToken(token: String) = validateToken(token, false)

    /**
     * Se encarga de validar el token sea o no de refresco
     * @param token Token a validad
     * @param isRefreshToken Si es o no un token de refresco
     * @return Devuelve verdadero si el token es válido o false si no lo es 
     */
    private fun validateToken(token: String, isRefreshToken: Boolean): Boolean {
        try {
            val claims = parser.parseClaimsJws(token)
            if (isRefreshToken != claims.body["refresh"])
                throw UnsupportedJwtException("No se ha utilizado el token apropiado")
            return true
        } catch (ex: Exception) {
            with(logger!!) {
                when (ex) {
                    is SignatureException -> info("Error en la firma del token ${ex.message}")
                    is MalformedJwtException -> info("Token malformado ${ex.message}")
                    is ExpiredJwtException -> info("Token expirado ${ex.message}")
                    is UnsupportedJwtException -> info("Token no soportado ${ex.message}")
                    is IllegalArgumentException -> info("Token incompleto (claims vacío) ${ex.message}")
                    else -> info("Error indeterminado")
                }
            }
            throw ex
        }
        return false
    }
}
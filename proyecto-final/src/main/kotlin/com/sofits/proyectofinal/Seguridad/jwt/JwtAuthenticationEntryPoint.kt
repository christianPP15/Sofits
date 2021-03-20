package com.sofits.proyectofinal.Seguridad.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.sofits.proyectofinal.ErrorControl.ApiError
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Clase estereotipada como componente que lanza una excepción en el caso de que alguien no este autorizada para la petición y que extiende a AuthenticationEntryPoint
 * @author lmlopezmagana
 * @see Component
 * @see AuthenticationEntryPoint
 */
@Component
class JwtAuthenticationEntryPoint(
    val mapper : ObjectMapper
)  : AuthenticationEntryPoint {

    /**
     * Crea una respuesta en el caso de que alguien no este autorizado para la petición
     */
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response?.status = 401
        response?.contentType = "application/json"
        response?.writer?.println(mapper.writeValueAsString(authException?.message?.let { ApiError(HttpStatus.UNAUTHORIZED,"Acceso denegado ${authException.message}") }))
    }

}
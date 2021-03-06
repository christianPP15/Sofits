package com.sofits.proyectofinal.Seguridad.jwt


import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Servicios.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Clase estereotipada como Componente y que extiende de OncePerRequestFilter
 * @see Component
 * @see OncePerRequestFilter
 * @author lmlopezmagana
 */
@Component
class JwtAuthorizationFilter() : OncePerRequestFilter() {
    /**
     * Proveedor del token
     */
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    /**
     * Servicios que gestionan los usuarios
     */
    @Autowired
    lateinit var userService: UserService

    /**
     * Extractor del token de la petición
     */
    @Autowired
    lateinit var bearerTokenExtractor: BearerTokenExtractor

    /**
     * Logger para mostrar información por la traza de la pila
     */
    private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

    /**
     * Filtro que obtiene el token de la petición e introduce al usuario en el contexto de la petición
     * @param request Petición del usuario
     * @param response Respuesta del servidor
     * @param filterChain Filtro en la petición
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            bearerTokenExtractor.getJwtFromRequest(request).ifPresent { token ->
                if (jwtTokenProvider.validateAuthToken(token)) {
                    val userId = jwtTokenProvider.getUserIdFromJWT(token)
                    val user : Usuario = userService.findById(userId).orElseThrow {
                        UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                    }
                    val authentication = UsernamePasswordAuthenticationToken(user, user.roles, user.authorities)
                    authentication.details = WebAuthenticationDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication

                }
            }
            //filterChain.doFilter(request, response)
        } catch (ex : Exception) {
            log.info("No se ha podido establecer la autenticación del usuario en el contexto de seguridad")
            throw ex
        } finally {
            filterChain.doFilter(request, response)
        }

    }


}

/**
 * Clase estereotipada como Service y que se encarga de la extracción del token
 * @see Service
 * @author lmlopezmagana
 */
@Service
class BearerTokenExtractor {
    /**
     * Extrae el token de la petición
     * @param request Petición del usuario del que extraer el token
     * @return Devuelve un optional vacío o bien un optional con el token
     */
    fun getJwtFromRequest(request: HttpServletRequest): Optional<String> {
        val bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX))
            Optional.of(
                bearerToken.substring(
                    JwtTokenProvider.TOKEN_PREFIX.length,
                    bearerToken.length
                )
            ) else Optional.empty()

    }
}
package com.sofits.proyectofinal.Seguridad

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Clase estereotipada como componente que extiende de OncePerRequestFilter y que actua como filtro en la seguridad
 * @see Component
 * @see OncePerRequestFilter
 * @author lmlopezmagana
 */
@Component
class FilterChainExceptionHandler() : OncePerRequestFilter() {
    /**
     * Logger para mostrar información por la traza de la pila
     */
    private val log : Logger = LoggerFactory.getLogger(FilterChainExceptionHandler::class.java)

    /**
     * Excepción para cuando algo falla en el filtro
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    lateinit var resolver : HandlerExceptionResolver

    /**
     * Creamos el filtro entre la petición y la respuesta
     * @param request Petición del usuario
     * @param response Respuesta del usuario
     * @param filterChain Filtro de la petición
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex : Exception) {
            log.error("Spring Security Filter Chain Exception", ex.message)
            resolver.resolveException(request!!, response!!, null, ex!!)
        }
    }

}
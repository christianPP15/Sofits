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

@Component
class FilterChainExceptionHandler() : OncePerRequestFilter() {

    private val log : Logger = LoggerFactory.getLogger(FilterChainExceptionHandler::class.java)

    @Autowired
    @Qualifier("handlerExceptionResolver")
    lateinit var resolver : HandlerExceptionResolver


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
package com.sofits.proyectofinal.ErrorControl

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletResponse


@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {


    @ExceptionHandler(value = [EntityNotFoundExceptionControl::class])
    fun handleExceptionEntityNotFound(ex: EntityNotFoundExceptionControl): ResponseEntity<ApiError> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(HttpStatus.BAD_REQUEST, ex.message))


    private val log: Logger = LoggerFactory.getLogger(GlobalRestControllerAdvice::class.java)

    @ExceptionHandler(value=[SignatureException::class, MalformedJwtException::class, ExpiredJwtException::class, UnsupportedJwtException::class])
    fun handleJwtExceptions(ex: JwtException, response: HttpServletResponse) : ResponseEntity<ApiError> {
        log.info("handleJwtExceptions")
        // Por alguna razón, la respuesta ha empezado a producirse
        // Hay que resetear la respuesta para poder enviar con el mecanismo unificado
        // el mensaje de error.
        response.reset()
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiError(HttpStatus.UNAUTHORIZED, ex.message))
    }


    @ExceptionHandler(value=[AuthenticationException::class])
    fun handleAuthenticationException(ex: AuthenticationException) : ResponseEntity<ApiError> {
        log.info("handleAuthenticationException")
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiError(HttpStatus.UNAUTHORIZED, ex.message))

    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: org.springframework.http.HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .body(
                ApiError(
                    status,
                    "Error de validación, asegurese de que todos los campos cumplen las normas establecidas",
                    ex.fieldErrors.map {
                        ApiValidationSubError(it.field, it.rejectedValue, it.defaultMessage)
                    }
                )
            )

    override fun handleExceptionInternal(
        ex: Exception,
        @Nullable body: Any?,
        headers: org.springframework.http.HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(status, ex.message)
        return ResponseEntity.status(status).body(apiError)
    }
}
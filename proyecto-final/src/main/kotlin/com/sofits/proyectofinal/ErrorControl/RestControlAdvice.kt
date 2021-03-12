package com.sofits.proyectofinal.ErrorControl

import com.sun.istack.Nullable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException


@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {


    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleExceptionEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ApiError> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(HttpStatus.BAD_REQUEST, ex.message))


    private val log: Logger = LoggerFactory.getLogger(GlobalRestControllerAdvice::class.java)


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
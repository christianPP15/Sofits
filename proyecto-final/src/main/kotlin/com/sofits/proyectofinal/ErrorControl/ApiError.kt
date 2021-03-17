package com.sofits.proyectofinal.ErrorControl

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

/**
 * Data class que define el módelo de los mensajes de error
 */
data class ApiError(
    /**
     * Estado de respuesta HTTP
     * @see HttpStatus
     */
    val estado: HttpStatus,
    /**
     * Mensaje de error para el usuario
     */
    val mensaje: String?,
    /**
     * Sub errores para dar más información al usuario
     * @see ApiSubError
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val subErrores: List<out ApiSubError>? = null,
    /**
     * Fecha y hora en la que se ha producido el fallo
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    val fecha: LocalDateTime = LocalDateTime.now(),
)

/**
 * Clase abstractar para tratar los sub-errores
 */
open abstract class ApiSubError

/**
 * Data class que definen los sub-errores
 */
data class ApiValidationSubError(
    /**
     * Campo sobre el que se produce el fallo
     */
    val campo : String,
    /**
     * Valor que se ha rechazado
     */
    val valorRechazado : Any?,
    /**
     * Mensaje de error
     */
    val mensaje : String?
) : ApiSubError()
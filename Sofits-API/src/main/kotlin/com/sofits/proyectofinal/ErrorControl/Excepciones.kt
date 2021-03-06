package com.sofits.proyectofinal.ErrorControl

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import java.util.*

/**
 * Data class que define los errores generales extendiendo de RuntimeException
 * @see RuntimeException
 */
open class EntityNotFoundExceptionControl(val msg: String) : RuntimeException(msg)

/**
 * Data class que define un mensaje de error para cuándo no encontramos un usuario por su id
 */
data class UserNotFoundById(val id: UUID) : EntityNotFoundExceptionControl("No se ha podido encontrar el usuario a partir de su ID")

/**
 * Data class que define si un error si un usuario ya existe, para ello comprobamos si el email ya existe
 */
data class UserAlreadyExit(val email:String) : EntityNotFoundExceptionControl("El nombre de usuario $email ya existe")

/**
 * Data class que define si el autor buscado por su id no existe
 */
data class AutorNotExist(val id:UUID) : EntityNotFoundExceptionControl("No existe un autor con id $id")

/**
 * Error para cuándo no se han encontrado autores
 */
data class AutorsNotExists(val mensaje: String="No existen autores aún") : EntityNotFoundExceptionControl(mensaje)

/**
 * Error para cuándo no se ha encontrado un libro por su id
 */
data class LibroNotExist(val id: UUID): EntityNotFoundExceptionControl("No existe un libro con id $id")

/**
 * Error para cuándo no se han encontrado libros
 */
data class LibrosNotExists(val mensaje: String="No existen libros aún") : EntityNotFoundExceptionControl(mensaje)

/**
 * Error para cuándo no se ha encontrado una publicación un el id proporcionado
 */
data class PublicacionNotExist(val id:UsuarioTieneLibroId): EntityNotFoundExceptionControl("No existen publicaciones para el id del libro ${id.libro_id} y el usuario ${id.usuario_id}")

/**
 * Error para cuándo no existe una valoración de un usuario hacia el otro
 */
data class ValoracionNotExist(val idUsuarioValorado: UUID, val idUsuarioValorando:UUID)
    : EntityNotFoundExceptionControl("No existe valoración para el usuario con id $idUsuarioValorado y el $idUsuarioValorando")

/**
 * Error para cuándo el usuario intenta agregar un libro que ya tiene agregado
 */
data class LibroYaExiste(val mensaje: String ="El libro que está intentando agregar ya existe para ese usuario") : EntityNotFoundExceptionControl(mensaje)

/**
 * Error para cuándo se realiza la busqueda de un libro y no hay resultados
 */
data class SearchLibroNoResultException(val mensaje: String ="No se encuentran libros con esas características"): EntityNotFoundExceptionControl(mensaje)

/**
 * Error para cuándo no existen géneros
 */
data class NoExistGender(val mensaje: String="No existen géneros aún") :EntityNotFoundExceptionControl(mensaje)

/**
 * Error para cuándo no existen géneros con el id proporcionado
 */
data class NoExistGenderById(val id:UUID,val mensaje: String="No existen géneros aún con el id $id") :EntityNotFoundExceptionControl(mensaje)
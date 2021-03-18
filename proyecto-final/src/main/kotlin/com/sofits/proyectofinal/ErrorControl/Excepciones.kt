package com.sofits.proyectofinal.ErrorControl

import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import java.util.*

open class EntityNotFoundExceptionControl(val msg: String) : RuntimeException(msg)

data class UserNotFoundById(val id: UUID) : EntityNotFoundExceptionControl("No se ha podido encontrar el usuario a partir de su ID")

data class UserAlreadyExit(val email:String) : EntityNotFoundExceptionControl("El nombre de usuario $email ya existe")

data class AutorNotExist(val id:UUID) : EntityNotFoundExceptionControl("No existe un autor con id $id")

data class AutorsNotExists(val mensaje: String="No existen autores aún") : EntityNotFoundExceptionControl(mensaje)

data class LibroNotExist(val id: UUID): EntityNotFoundExceptionControl("No existe un libro con id $id")

data class LibrosNotExists(val mensaje: String="No existen libros aún") : EntityNotFoundExceptionControl(mensaje)

data class PublicacionNotExist(val id:UsuarioTieneLibroId): EntityNotFoundExceptionControl("No existen publicaciones para el id $id")

data class ValoracionNotExist(val idUsuarioValorado: UUID, val idUsuarioValorando:UUID)
    : EntityNotFoundExceptionControl("No existe valoración para el usuario con id $idUsuarioValorado y el $idUsuarioValorando")

data class LibroYaExiste(val mensaje: String ="El libro que está intentando agregar ya existe para ese usuario") : EntityNotFoundExceptionControl(mensaje)

data class SearchProductoNoResultException(val mensaje: String ="No se encuentra productos con esas características"): EntityNotFoundExceptionControl(mensaje)
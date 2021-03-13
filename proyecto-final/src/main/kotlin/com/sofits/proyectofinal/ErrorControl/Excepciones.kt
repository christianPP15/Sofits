package com.sofits.proyectofinal.ErrorControl

import java.util.*

open class EntityNotFoundExceptionControl(val msg: String) : RuntimeException(msg)

data class UserNotFoundById(val id: UUID) : EntityNotFoundExceptionControl("No se ha podido encontrar el usuario a partir de su ID")

data class UserAlreadyExit(val email:String) : EntityNotFoundExceptionControl("El nombre de usuario $email ya existe")

data class AutorNotExist(val id:UUID) : EntityNotFoundExceptionControl("No existe un autor con id $id")

data class AutorsNotExists(val mensaje: String="No existen autores aún") : EntityNotFoundExceptionControl(mensaje)
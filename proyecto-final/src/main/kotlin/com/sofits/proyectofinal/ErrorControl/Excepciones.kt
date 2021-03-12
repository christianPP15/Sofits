package com.sofits.proyectofinal.ErrorControl

import java.util.*

open class EntityNotFoundException(val msg: String) : RuntimeException(msg)

data class UserNotFoundById(val id: UUID) : EntityNotFoundException("No se ha podido encontrar el usuario a partir de su ID")

data class UserAlreadyExit(val email:String) : EntityNotFoundException("El nombre de usuario $email ya existe")
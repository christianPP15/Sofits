package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.toDto
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UsuarioTieneLibroServicio : BaseService<UsuarioTieneLibro,UsuarioTieneLibroId,UsuarioTieneLibroRepository>(){


    fun getMyBooks(pageable: Pageable, user: Usuario?) =
        ResponseEntity.ok(repositorio.getAllBooksFromUser(pageable,user!!).map { it.toDto() }.takeIf { user!=null } ?: throw LibrosNotExists())



}
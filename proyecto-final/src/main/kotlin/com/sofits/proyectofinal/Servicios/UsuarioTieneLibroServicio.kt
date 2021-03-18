package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.DTO.AgregarLibroAUsuario
import com.sofits.proyectofinal.DTO.EditarLibroAUsuario
import com.sofits.proyectofinal.DTO.LibrosUsuariosResponse
import com.sofits.proyectofinal.DTO.toDto
import com.sofits.proyectofinal.ErrorControl.EntityNotFoundExceptionControl
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.ErrorControl.LibroYaExiste
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.util.*

@Service
class UsuarioTieneLibroServicio(val libroService: LibroService) :
    BaseService<UsuarioTieneLibro, UsuarioTieneLibroId, UsuarioTieneLibroRepository>() {
    @Autowired
    lateinit var  servicioImagenes: ImagenServicio

    fun getMyBooks(pageable: Pageable, user: Usuario?) =
        repositorio.getAllBooksFromUser(pageable, user!!).map { it.toDto() }.takeIf { user != null }
            ?: throw LibrosNotExists()

    fun getAllBooksEquals(pageable: Pageable, id: UUID) =
        repositorio.getAllBooksEquals(pageable, libroService.findById(id)
                .orElseThrow { LibroNotExist(id) }).map { it.toDto() }.takeIf { libroService.existsById(id) }
                ?: throw LibrosNotExists()


    fun addBookUser(user: Usuario,idLibro:UUID, libroAgregar: AgregarLibroAUsuario,file: MultipartFile): LibrosUsuariosResponse {
        val book = libroService.findById(idLibro).orElseThrow { LibroNotExist(idLibro) }
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, book.id!!)
        if (repositorio.existsById(idLibroUsuario)) {
            throw LibroYaExiste()
        }else{
            val usuarioLibro = UsuarioTieneLibro(
                    idLibroUsuario,
                    user,
                    book,
                    libroAgregar.DescripccionLibro,
                    libroAgregar.estado,
                    libroAgregar.idioma,
                    libroAgregar.edicion.toInt()
            )
            val imagen=servicioImagenes.save(file)
            usuarioLibro.imagen=imagen
            return repositorio.save(usuarioLibro).toDto()
        }

    }

    fun changeState(user: Usuario, id: UUID) {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        val publicacion = repositorio.findById(idLibroUsuario).orElseThrow { LibroNotExist(id) }
        publicacion.intercambiado = !publicacion.intercambiado
        repositorio.save(publicacion).toDto()
    }

    fun editLibroUser(user: Usuario, id: UUID, libroAgregar: EditarLibroAUsuario): Optional<LibrosUsuariosResponse> {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        return repositorio.findById(idLibroUsuario).map { publicacion ->
            publicacion.DescripccionLibro = libroAgregar.DescripccionLibro
            publicacion.edicion = libroAgregar.edicion.toInt()
            publicacion.estado = libroAgregar.estado
            publicacion.idioma = libroAgregar.idioma
            repositorio.save(publicacion).toDto()
        }
    }

    fun removeBookFromUser(user: Usuario, id: UUID) {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        if (repositorio.existsById(idLibroUsuario))
            repositorio.deleteById(idLibroUsuario)
    }

    fun removeBookFromUserAdmin(userId:UUID,libroId:UUID){
        val id= UsuarioTieneLibroId(userId,libroId)
        if (existsById(id))
            repositorio.deleteById(id)
    }
}
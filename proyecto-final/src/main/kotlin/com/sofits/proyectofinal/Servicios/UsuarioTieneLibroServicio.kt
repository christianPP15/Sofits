package com.sofits.proyectofinal.Servicios


import com.sofits.proyectofinal.DTO.AgregarLibroAUsuario
import com.sofits.proyectofinal.DTO.LibrosUsuariosResponse
import com.sofits.proyectofinal.ErrorControl.LibroNotExist
import com.sofits.proyectofinal.ErrorControl.LibroYaExiste
import com.sofits.proyectofinal.ErrorControl.LibrosNotExists
import com.sofits.proyectofinal.DTO.*
import com.sofits.proyectofinal.ErrorControl.*
import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibro
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroId
import com.sofits.proyectofinal.Modelos.UsuarioTieneLibroRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Clase estereotipada como servicio que se encarga de la gestión de los ejemplares de un usuario y que extiende de BaseService
 * @author christianPP15
 * @see BaseService
 * @see Service
 */
@Service
class UsuarioTieneLibroServicio(
    /**
     * Servicios para la gestión de libros
     * @see LibroService
     */
    val libroService: LibroService,
    /**
     * Servicio para la gestión de las imagenes
     * @see ImagenServicio
     */
    val  servicioImagenes: ImagenServicio) :
    BaseService<UsuarioTieneLibro, UsuarioTieneLibroId, UsuarioTieneLibroRepository>() {
    /**
     * Obtener todos los ejemplares publicados por el usuario logueado
     * @param user Usuario logueado
     * @param pageable Atributo que da la oportunidad de paginar los resultados de los libros
     * @return Page de los ejemplares subidos
     */
    fun getMyBooks(pageable: Pageable, user: Usuario?) =
        repositorio.getAllBooksFromUser(pageable, user!!).map { it.toDtoMyBook() }.takeIf { user != null }
            ?: throw LibrosNotExists()




    /**
     * Obtener todos los ejemplares de un libro
     * @param id Identificador del libro al que pertenecen los ejemplares
     * @param user Usuario que realiza la petición
     * @throws LibroNotExist Si el libro no existe
     * @return Devuelve todos los ejemplares de un libro de forma paginada
     */
    fun getAllBooksEquals( id: UUID,user: Usuario?): PublicacionesResponse {
        val libro = libroService.findById(id).orElseThrow { LibroNotExist(id) }
        val result=repositorio.getAllBooksEquals( libroService.findById(id)
            .orElseThrow { LibroNotExist(id) }).map { it.toDtoAux() }.takeIf { libroService.existsById(id) }
            ?: throw LibrosNotExists()
        return PublicacionesResponse(libro.toDetailPublicacion(user),result)
    }


    /**
     * Agregamos un nuevo ejemplar a un usuario
     * @param user Usuario que esta registrando el ejemplar
     * @param idLibro Id del libro al cual pertenece el ejemplar
     * @param libroAgregar Datos del ejemplar que estamos registrando
     * @see AgregarLibroAUsuario
     * @param file Imagen subida por el usuario del libro que está publicando
     * @throws LibroYaExiste Si el ejemplar que está intentando registrar ya existe
     * @return Devuelve el ejemplar ya registrado
     */
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
            return repositorio.save(usuarioLibro).toDtoAux()
        }

    }

    /**
     * Cambia el estado de un libro de intercambiado a no intercambiado
     * @param user Usuario logueado que esta cambiando el estado
     * @param id Identificador del libro al que pertenece el ejemplar
     * @throws LibroNotExist Si no existe el ejemplar que se esta intentando modificar
     */
    fun changeState(user: Usuario, id: UUID) {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        val publicacion = repositorio.findById(idLibroUsuario).orElseThrow { LibroNotExist(id) }
        publicacion.intercambiado = !publicacion.intercambiado
        repositorio.save(publicacion).toDtoAux()
    }

    /**
     * Edita la información de un ejemplar subido por un usuario
     * @param user Usuario logueado
     * @param id Identificador del libro al que pertenece el ejemplar
     * @param AgregarLibroAUsuario
     * @return Devuelve el ejemplar editado
     */
    fun editLibroUser(user: Usuario, id: UUID, libroAgregar: AgregarLibroAUsuario): Optional<LibrosUsuariosResponse> {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        return repositorio.findById(idLibroUsuario).map { publicacion ->
            publicacion.DescripccionLibro = libroAgregar.DescripccionLibro
            publicacion.edicion = libroAgregar.edicion.toInt()
            publicacion.estado = libroAgregar.estado
            publicacion.idioma = libroAgregar.idioma
            repositorio.save(publicacion).toDtoAux()
        }
    }

    /**
     * Elimina la publicación de un ejemplar subida por el propio usuario que realiza la petición
     * @param user Usuario logueado
     * @param id Identificador del libro a eliminar
     */
    fun removeBookFromUser(user: Usuario, id: UUID) {
        val idLibroUsuario = UsuarioTieneLibroId(user.id!!, id)
        if (repositorio.existsById(idLibroUsuario))
            repositorio.deleteById(idLibroUsuario)
    }
    /**
     * Administrador elimina una publicación subida por cualquier usuario
     * @param userId Identificador del usuario al que pertenece la publicación
     * @param id Identificador del libro a eliminar
     */
    fun removeBookFromUserAdmin(userId:UUID,libroId:UUID){
        val id= UsuarioTieneLibroId(userId,libroId)
        if (existsById(id))
            repositorio.deleteById(id)
    }

    /**
     * Obtener una publicación en base al id del libro y del usuario
     * @param idLibro Identificador del libro
     * @param idUsuario Identificador del usuario
     * @return Una publicación
     * @throws PublicacionNotExist Si la publicación no existe
     */
    fun getPublicacionById(idLibro: UUID,idUsuario:UUID): LibrosUsuariosResponseDetail {
        val id = UsuarioTieneLibroId(idUsuario,idLibro)
        return repositorio.findById(id).orElseThrow { PublicacionNotExist(id) }.toDetalleDto()
    }
}
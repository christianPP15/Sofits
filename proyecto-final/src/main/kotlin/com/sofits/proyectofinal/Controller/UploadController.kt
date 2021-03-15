package com.sofits.proyectofinal.Controller

import com.sofits.proyectofinal.Modelos.Imagenes
import com.sofits.proyectofinal.Servicios.ImagenServicio
import com.sofits.proyectofinal.upload.ImgurBadRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/imagen/")
class UploadController(
    private val servicio: ImagenServicio
) {

    @GetMapping("/")
    fun getAll() : List<EntidadDto> {
        val result = servicio.findAll()
        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay registros")
        return result.map { it.toDto() }

    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID) : EntidadDto =
        servicio.findById(id).map { it.toDto() }
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad $id no encontrada") }


    @PostMapping("/")
    fun create(@RequestPart("file") file: MultipartFile ) : ResponseEntity<EntidadDto> {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicio.save(file).toDto())
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }

    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) : ResponseEntity<Void> {
        servicio.deleteById(id)
        return ResponseEntity.noContent().build()
    }


}


data class EntidadDto(
    val imageId: String?,
    val id: UUID?
)

fun Imagenes.toDto() = EntidadDto( img?.id, id)
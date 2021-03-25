package com.sofits.proyectofinal.upload

import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

/**
 * Controlador para cargar imagenes desde Imgur
 * @author lmlopezmagana
 */
@RestController
class ImgurFilesController(
    private val imgurStorageService: ImgurStorageService
) {
    /**
     * Método encargado de devolver la imagen a través del dataId de una imagen
     * @param id Data id de una imagen
     * @return Devuelve un recurso con la imagen
     * @throws ResponseStatusException Excepción para cuando una imagen no se encuentra
     */
    @Throws(ResponseStatusException::class)
    @GetMapping("/files/{id}")
    fun get(@PathVariable id: String) : ResponseEntity<Resource> {
        var resource: Optional<MediaTypeUrlResource>
        try {
            resource = imgurStorageService.loadAsResource(id)
            if (resource.isPresent) {
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.get().mediaType)).body(resource.get())
            }
            return ResponseEntity.noContent().build()
        } catch (ex: ImgurImageNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada")
        }

    }


}
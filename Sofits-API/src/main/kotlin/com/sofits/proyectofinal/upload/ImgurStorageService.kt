package com.sofits.proyectofinal.upload

import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.*

interface ImgurStorageService : BasicImageStorageService<ImgurImageAttribute, String, String>

/**
 * Servicio que implementa los métodos ImgurStorageService y que recibe el servicio ImgurService
 * @see ImgurStorageService
 * @see ImgurService
 */
@Service
class ImgurStorageServiceImpl(
    private val imgurService: ImgurService
)
    : ImgurStorageService {
    /**
     * Logger para mostrar información por la trasa de la pila
     */
    val logger: Logger = LoggerFactory.getLogger(ImgurStorageService::class.java)

    /**
     * Guarda la imagen convirtiendola a base 64
     * @return Devuelve un optional vacío o un optional con ImgurImageAttribute
     * @see ImgurImageAttribute
     */
    override fun store(file: MultipartFile) : Optional<ImgurImageAttribute> {

        if (!file.isEmpty) {
            var imgReq =
                NewImageReq(Base64.getEncoder().encodeToString(file.bytes),
                    /*imgToBase64Data(file),*/
                    file.originalFilename.toString())
            var imgRes = imgurService.upload(imgReq)
            if(imgRes.isPresent)
                return Optional.of(ImgurImageAttribute(imgRes.get().data.id, imgRes.get().data.deletehash))
        }

        return Optional.empty()

    }

    /**
     * Carga la imagen a partir de su id
     * @return Devuelve un optional vacío o un Optional con el recurso
     */
    override fun loadAsResource(id: String) : Optional<MediaTypeUrlResource> {
        var response = imgurService.get(id)
        if (response.isPresent) {
            var resource = MediaTypeUrlResource(response.get().data.type, URI.create(response.get().data.link))
            if (resource.exists() || resource.isReadable)
                return Optional.of(resource)
        }

        return Optional.empty()

    }

    /**
     * Elimina una imagen en base a su hash
     */
    override fun delete(deletehash: String) : Unit {
        logger.debug("Eliminando la imagen $deletehash")
        imgurService.delete(deletehash)
    }


}

/**
 * Clase que almacena el tipo del archivo y su uri
 */
class MediaTypeUrlResource(
    /**
     * Atributo que almacena el tipo de la imagen
     */
    val mediaType: String,
    /**
     * Atritubo que almacena la uri de la imagen
     */
    var uri: URI) : UrlResource(uri)
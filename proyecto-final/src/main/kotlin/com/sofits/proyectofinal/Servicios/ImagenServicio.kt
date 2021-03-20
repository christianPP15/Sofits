package com.sofits.proyectofinal.Servicios

import com.sofits.proyectofinal.Modelos.Imagenes
import com.sofits.proyectofinal.Modelos.ImagenesRepository
import com.sofits.proyectofinal.Servicios.base.BaseService
import com.sofits.proyectofinal.upload.ImgurImageAttribute
import com.sofits.proyectofinal.upload.ImgurStorageService
import com.sofits.proyectofinal.upload.ImgurStorageServiceImpl
import org.springframework.stereotype.Service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Clase estereotipada como servicio que se encarga de la gestión de la subida de imagenes y que extiende de BaseService
 * @author lmlopezmagana
 * @see BaseService
 * @see Service
 */
@Service
class ImagenServicio(
    /**
     * Servicio de procesamiento de la imagen
     */
    private val imageStorageService: ImgurStorageServiceImpl
) : BaseService<Imagenes, UUID, ImagenesRepository>() {
    /**
     * Crea la instancia de un log para mostrar información por la traza de la pila
     */
    val logger: Logger = LoggerFactory.getLogger(Imagenes::class.java)

    /**
     * Método encargado de almacenar una imagen
     * @param file Imagen que intentamos almacenar
     * @return Devuelve los datos de la imagen almacenada
     */
    fun save(file: MultipartFile) : Imagenes {
        var imageAttribute : Optional<ImgurImageAttribute> = Optional.empty()
        if (!file.isEmpty) {
            imageAttribute = imageStorageService.store(file)
        }
        val e = Imagenes()
        e.img = imageAttribute.orElse(null)
        return save(e)
    }

    /**
     * Elimina la entidad de una imagen
     * @param e Imagen a eliminar
     */
     override fun delete(e : Imagenes) {
        logger.debug("Eliminando la entidad $e")
        e.img?.let { it.deletehash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(e)
    }

}
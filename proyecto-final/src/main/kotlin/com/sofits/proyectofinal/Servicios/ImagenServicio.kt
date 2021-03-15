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

@Service
class ImagenServicio(
    private val imageStorageService: ImgurStorageServiceImpl
) : BaseService<Imagenes, UUID, ImagenesRepository>() {

    val logger: Logger = LoggerFactory.getLogger(Imagenes::class.java)


    fun save(file: MultipartFile) : Imagenes {
        var imageAttribute : Optional<ImgurImageAttribute> = Optional.empty()
        if (!file.isEmpty) {
            imageAttribute = imageStorageService.store(file)
        }
        val e = Imagenes()
        e.img = imageAttribute.orElse(null)
        return save(e)
    }

     override fun delete(e : Imagenes) {
        logger.debug("Eliminando la entidad $e")
        e.img?.let { it.deletehash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(e)
    }

}
package com.sofits.proyectofinal.upload

import com.sofits.proyectofinal.Modelos.Imagenes
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * Data class que almacena los datos convenientes para el tratamiento de las imagenes su id y su hash de borrado
 */
data class ImgurImageAttribute(var id: String?, var deletehash : String?)

/**
 * Data class que almacena el UUID de almacenamiento de la imagen y el identificador de la imagen
 */
data class ImagenWithoutHash(
    val id:UUID?,
    val idImagen:String?
)

/**
 * Función que convierte una imagen a una nueva entidad que no contiene el hash de borrado
 * @see ImagenWithoutHash
 */
fun Imagenes.toDto()= ImagenWithoutHash(id,img!!.id)

/**
 * Convertidor que convierte los datos de una imagen a un string
 * @see AttributeConverter
 */
@Converter(autoApply = true)
class ImgurImageAttributeToStringConverter() : AttributeConverter<ImgurImageAttribute, String?> {

    companion object {
        /**
         * Definición del separador
         */
        private const val SEPARATOR = ", "
    }

    /**
     * Función que convierte a una columna de la base de datos una imagen
     * @see ImgurImageAttribute
     */
    override fun convertToDatabaseColumn(attribute: ImgurImageAttribute?): String? {
        if (attribute == null) return null
        return attribute.id + SEPARATOR + attribute.deletehash
    }

    /**
     * Función que convierte un objeto en una entidad
     */
    override fun convertToEntityAttribute(dbData: String?): ImgurImageAttribute? {
        if (dbData == null) return null

        var pieces = dbData.split(SEPARATOR)

        if (pieces.isEmpty())
            return null

        var first = if (pieces[0].isNotEmpty()) pieces[0] else null
        var second: String? = if (pieces.size >= 2 && pieces[1].isNotEmpty()) pieces[1] else null

        return ImgurImageAttribute(first, second)
    }

}
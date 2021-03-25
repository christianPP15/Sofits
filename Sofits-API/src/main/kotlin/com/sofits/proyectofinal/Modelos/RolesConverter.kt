package com.sofits.proyectofinal.Modelos

import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * Clase que actua como convertidor de una lista de string a un solo string
 * @see AttributeConverter
 * @see Converter
 */
@Converter
class RolesConverter (): AttributeConverter<List<String>, String> {
    /**
     * Convierte el valor almacenado en el atributo de la entidad en la representación de los datos para ser almacenados en la base de datos.
     */
    override fun convertToDatabaseColumn(list: List<String>?): String? {
        return java.lang.String.join(",", list)
    }

    /**
     * Convierte los datos almacenados en la columna de la base de datos en el valor que se almacenará en el atributo de la entidad.
     */
    override fun convertToEntityAttribute(joined: String): List<String>? = joined.split(",")


}
package com.sofits.proyectofinal.Modelos

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class RolesConverter (): AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(list: List<String>?): String? {
        return java.lang.String.join(",", list)
    }
    override fun convertToEntityAttribute(joined: String): List<String>? = joined.split(",")


}
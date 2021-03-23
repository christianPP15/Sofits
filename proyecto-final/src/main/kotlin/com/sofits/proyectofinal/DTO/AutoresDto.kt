package com.sofits.proyectofinal.DTO

import com.fasterxml.jackson.annotation.JsonFormat
import com.sofits.proyectofinal.Modelos.Autor
import com.sofits.proyectofinal.upload.ImagenWithoutHash
import com.sofits.proyectofinal.upload.toDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

/**
 * Clase que define el modelo de respuesta con la información básica del autor
 * @property id Identificador  del autor
 * @property nombre Nombre del autor
 * @property libros Libros escritor por dicho autor
 * @property imagen Imagen del autor que no incluye el hash
 * @see ImagenWithoutHash
 * @see LibroDtoDetailAutor
 */
data class AutoresDto(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre:String,
    @ApiModelProperty(value = "Libros escritos por el autor",dataType = "LibroDtoInicio",position = 3)
    val libros: List<LibroDtoDetailAutor> = mutableListOf(),
    val imagen: ImagenWithoutHash?
)

/**
 * Data class que incluye todos los datos básicos agregandole todos los datos de interés sobre el autor
 * @property id Identificador del autor
 * @property nombre Nombre del autor
 * @property biografia Biografía del autor
 * @property nacimiento Fecha de nacimiento del autor
 * @property libros Libros escritos por el autor
 * @see LibroDtoInicio
 */
data class AutoresDetail(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id:UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
    val biografia:String?,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
    @JsonFormat(pattern="yyyy-MM-dd")
    val nacimiento:LocalDate? = null,
    @ApiModelProperty(value = "Libros escritos por el autor",dataType = "LibroDtoInicio",position = 5)
    val libros: List<LibroDtoInicio> = mutableListOf()
)

/**
 * Data class que incluye la información del autor con su imagen
 * @property id Identificador del autor
 * @property nombre Nombre del autor
 * @property biografia Biografía del autor
 * @property nacimiento Fecha de nacimiento del autor
 * @property imagen Imagen del autor sin hash de eliminación
 * @see ImagenWithoutHash
 */
data class AutorDatosBiograficos(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 3)
    val biografia: String?=null,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.util.LocalDate",position = 4)
    @JsonFormat(pattern="yyyy-MM-dd")
    val nacimiento: String?=null,
    @ApiModelProperty(value = "Imagen del autor",dataType = "ImagenWithoutHash",position = 5)
    val imagen: ImagenWithoutHash?,
)

/**
 * Data class que incluye el nombre y el identificador del autor
 * @property id Identificador del autor
 * @property nombre Nombre del autor
 */
data class AutorDeLibro(
    @ApiModelProperty(value = "Identificador del autor",dataType = "java.util.UUID",position = 1)
    val id: UUID,
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 2)
    val nombre: String
)

/**
 * Función que convierte un autor a AutorDeLibro
 * @see AutorDeLibro
 */
fun Autor.toLibroCreate() = AutorDeLibro(id!!,nombre)

/**
 * Función que convierte un autor a AutoresDto
 * @see AutoresDto
 */
fun Autor.toDto() = AutoresDto(id!!,nombre,libros.map { it.toDetailAutor() },imagen?.toDto())

/**
 * Función que convierte un autor a AutoresDetail
 * @see AutoresDetail
 */
fun Autor.toDetail() = AutoresDetail(id!!,nombre,Biografia, nacimiento,libros.map { it.toDtoAutor() })

/**
 * Función que convierte un autor a AutorDatosBiograficos
 * @see AutorDatosBiograficos
 */
fun Autor.toCrateDto()= AutorDatosBiograficos(id!!,nombre,Biografia,nacimiento.toString(),imagen?.toDto())

/**
 * Data class que define la información que se va a recibir para crear un Autor
 * @property nombre Nombre del autor
 * @property biografia Biografía del autor
 * @property nacimiento Fecha de nacimiento del autor
 */
data class createAutorComplete(
    @ApiModelProperty(value = "Nombre del autor",dataType = "java.lang.String",position = 1)
    @get:NotBlank(message = "{autor.nombre.notBlank}")
    val nombre: String,
    @ApiModelProperty(value = "Biografía del autor",dataType = "java.lang.String",position = 2)
    @get:NotBlank(message = "{autor.biografia.notBlank}")
    val biografia: String,
    @ApiModelProperty(value = "Fecha de nacimiento del autor",dataType = "java.lang.String",position = 4)
    val nacimiento: String? = null
)
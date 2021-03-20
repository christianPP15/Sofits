package com.sofits.proyectofinal.upload

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilderFactory
import java.net.URI
import java.util.*

/**
 * Servicio que recibe el clientId de imgur
 */
@Service
class ImgurService(
    /**
     * ClienteId que obtiene el valor de un fichero de properties
     * @see Value
     */
    @Value("\${imgur.clientid}") val clientId: String

) {
    /**
     * Atributo que define un RestTemplate
     */
    var restTemplate: RestTemplate = RestTemplate()

    init {
        /**
         * Define los errores para el RestTemplate
         */
        restTemplate.errorHandler = object : DefaultResponseErrorHandler() {
            override fun hasError(response: ClientHttpResponse) =
                response.statusCode.series() === HttpStatus.Series.CLIENT_ERROR || response.statusCode.series() === HttpStatus.Series.SERVER_ERROR


            override fun handleError(url: URI, method: HttpMethod, response: ClientHttpResponse) {
                when(response.statusCode) {
                    HttpStatus.INTERNAL_SERVER_ERROR -> throw RuntimeException("Error de servidor")
                    HttpStatus.BAD_REQUEST -> throw ImgurBadRequest()
                    HttpStatus.NOT_FOUND -> throw ImgurImageNotFoundException()
                }

            }

        }
    }

    /**
     * Constructor de uris
     */
    private var factory: UriBuilderFactory = DefaultUriBuilderFactory()

    /**
     * Logger para mostrar la información al usuario
     */
    val logger: Logger = LoggerFactory.getLogger(ImgurService::class.java)

    companion object {
        /**
         * Url base para obtener las imagenes
         */
        private const val BASE_URL = "https://api.imgur.com/3/image"

        /**
         * Url dónde guardar las imagenes
         */
        const val URL_NEW_IMAGE = "$BASE_URL"

        /**
         * URL para el borrado de imagenes
         */
        const val URL_DELETE_IMAGE = "$BASE_URL/{hash}"

        /**
         * Url para obtener las imagenes
         */
        const val URL_GET_IMAGE = "$BASE_URL/{id}"

        /**
         * Códigos de respuesta para los métodos POST y GET
         */
        const val SUCCESS_UPLOAD_STATUS = 200
        const val SUCCESS_GET_STATUS = 200
    }

    /**
     * Función que recibe la imagen y la procesa para saber si se ha subido correctamente
     * @return Devuelve un optional vacío o en su defecto un Obtional con un NewImageRes
     */
    fun upload(imageReq: NewImageReq): Optional<NewImageRes> {

        var headers = getHeaders()


        var request: HttpEntity<NewImageReq> = HttpEntity(imageReq, headers)

        var imageRes: NewImageRes? = restTemplate.postForObject(URL_NEW_IMAGE, request, NewImageRes::class.java)

        if (imageRes != null && imageRes.status == SUCCESS_UPLOAD_STATUS)
            return Optional.of(imageRes)
        return Optional.empty()

    }

    /**
     * Elimina la imagen a través de su hash de eliminación
     */
    fun delete(hash: String): Unit {
        logger.debug("Realizando la petición DELETE para eliminar la imagen $hash")


        var uri: URI = factory.uriString(URL_DELETE_IMAGE).build(hash)
        var request: RequestEntity<Void> = RequestEntity.delete(uri).headers(getHeaders()).build()

        restTemplate.exchange(request, Void::class.java)


    }

    /**
     * Función para obtener una imagen en base a su id
     * @return Devuelve un optional vacío o bien un Optional con la imagen
     */
    fun get(id: String): Optional<GetImageRes> {


        var uri: URI = factory.uriString(URL_GET_IMAGE).build(id)
        var request: RequestEntity<Void> = RequestEntity
            .get(uri)
            .headers(getHeaders())
            .accept(MediaType.APPLICATION_JSON)
            .build()

        var response = restTemplate.exchange(request, GetImageRes::class.java)


        if (response.statusCode.is2xxSuccessful && response.hasBody())
            return Optional.of(response.body as GetImageRes)
        return Optional.empty()
    }

    /**
     * Interceptor en el que se añade el cliente id en la cabecera de la petición
     * @return Devuelve la cabecera de la petición
     */
    private fun getHeaders(): HttpHeaders {
        var headers: HttpHeaders = HttpHeaders()
        headers["Authorization"] = "Client-ID $clientId"
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        return headers
    }


}

/**
 * Error para cuando una imagen no se ha encontrado
 */
class ImgurImageNotFoundException() : RuntimeException("No se ha podido encontrar la imagen")

/**
 * Error para cuando no se puede realizar la petición
 */
class ImgurBadRequest() : RuntimeException("Error al realizar la petición")

/**
 * Data class que define la imagen y su nombre
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageReq(
    var image: String,
    var name: String

)

/**
 * Data class que define la respuesta que tendrá al subir la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageRes(
    val success: String,
    val status: Int,
    val data: NewImageInfo
)

/**
 * Data class que almacena la información de una nueva imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageInfo(
    val link: String,
    val id: String,
    val deletehash: String
)

/**
 * Data class que almacena la respuesta al intentar acceder a una imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class GetImageRes(
    val success: String,
    val status: Int,
    val data: GetImageInfo
)

/**
 * Data class que almacena la información de una imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class GetImageInfo(
    val link: String,
    val id: String,
    val type: String
)
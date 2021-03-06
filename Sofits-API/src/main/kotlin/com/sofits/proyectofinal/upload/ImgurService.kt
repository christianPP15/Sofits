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

            /**
             * Sobreescribe las excepciones para lanzar algunas personalizadas
             */
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
     * Logger para mostrar la informaci??n al usuario
     */
    val logger: Logger = LoggerFactory.getLogger(ImgurService::class.java)

    companion object {
        /**
         * Url base para obtener las imagenes
         */
        private const val BASE_URL = "https://api.imgur.com/3/image"

        /**
         * Url d??nde guardar las imagenes
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
         * C??digos de respuesta para los m??todos POST y GET
         */
        const val SUCCESS_UPLOAD_STATUS = 200
        const val SUCCESS_GET_STATUS = 200
    }

    /**
     * Funci??n que recibe la imagen y la procesa para saber si se ha subido correctamente
     * @param imagen que se intenta subir
     * @return Devuelve un optional vac??o o en su defecto un Obtional con un NewImageRes
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
     * Elimina la imagen a trav??s de su hash de eliminaci??n
     * @param hash de eliminaci??n de la imagen
     */
    fun delete(hash: String): Unit {
        logger.debug("Realizando la petici??n DELETE para eliminar la imagen $hash")


        var uri: URI = factory.uriString(URL_DELETE_IMAGE).build(hash)
        var request: RequestEntity<Void> = RequestEntity.delete(uri).headers(getHeaders()).build()

        restTemplate.exchange(request, Void::class.java)


    }

    /**
     * Funci??n para obtener una imagen en base a su id
     * @param id Identificador de la imagen a construir
     * @return Devuelve un optional vac??o o bien un Optional con la imagen
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
     * Interceptor en el que se a??ade el cliente id en la cabecera de la petici??n
     * @return Devuelve la cabecera de la petici??n
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
 * Error para cuando no se puede realizar la petici??n
 */
class ImgurBadRequest() : RuntimeException("Error al realizar la petici??n")

/**
 * Data class que define la imagen y su nombre
 * @property image Imagen que se va a subir,
 * @property name Nombre de la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageReq(
    var image: String,
    var name: String

)

/**
 * Data class que define la respuesta que tendr?? al subir la imagen
 *  @property success Mensaje de respuesta
 *  @property status C??digo de respuesta
 *  @property data Informaci??n sobre la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageRes(
    val success: String,
    val status: Int,
    val data: NewImageInfo
)

/**
 * Data class que almacena la informaci??n de una nueva imagen
 * @property link Link de la imagen
 * @property id Id de la imagen
 * @property deletehash Hash de borrado de la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class NewImageInfo(
    val link: String,
    val id: String,
    val deletehash: String
)


/**
 * Data class que almacena la respuesta al intentar acceder a una imagen
 *  @property success Mensaje de respuesta
 *  @property status C??digo de respuesta
 *  @property data Informaci??n sobre la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class GetImageRes(
    val success: String,
    val status: Int,
    val data: GetImageInfo
)

/**
 * Data class que almacena la informaci??n de una imagen
 * @property link Link de la imagen
 * @property id Id de la imagen
 * @property type Tipo de la imagen
 */
@JsonIgnoreProperties(ignoreUnknown = false)
data class GetImageInfo(
    val link: String,
    val id: String,
    val type: String
)
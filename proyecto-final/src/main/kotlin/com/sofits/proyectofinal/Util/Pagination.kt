package com.sofits.proyectofinal.Util

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * Componente que permite facilitar el uso de la páginación en nuestra api
 * mandando los enlaces terminados para navegar entre las páginas
 * @author lmlopezmagana
 * @see
 */
@Component
class PaginationLinksUtils {
    /**
     * Métodos que crea las cabeceras de los enlaces
     */
    fun createLinkHeader(page: Page<*>, uriBuilder: UriComponentsBuilder): String? {
        val linkHeader = StringBuilder()
        linkHeader.append("")
        if (page.hasNext()) {
            val uri = constructUri(page.number + 1, page.size, uriBuilder)
            linkHeader.append(buildLinkHeader(uri, "next"))
        }
        if (page.hasPrevious()) {
            val uri = constructUri(page.number - 1, page.size, uriBuilder)
            appendCommaIfNecessary(linkHeader)
            linkHeader.append(buildLinkHeader(uri, "prev"))
        }
        if (!page.isFirst) {
            val uri = constructUri(0, page.size, uriBuilder)
            appendCommaIfNecessary(linkHeader)
            linkHeader.append(buildLinkHeader(uri, "first"))
        }
        if (!page.isLast) {
            val uri = constructUri(page.totalPages - 1, page.size, uriBuilder)
            appendCommaIfNecessary(linkHeader)
            linkHeader.append(buildLinkHeader(uri, "last"))
        }
        return linkHeader.toString()
    }

    /**
     * Método que construye la uri
     */
    private fun constructUri(newPageNumber: Int, size: Int, uriBuilder: UriComponentsBuilder): String {
        return uriBuilder.replaceQueryParam("page", newPageNumber).replaceQueryParam("size", size).build().encode()
            .toUriString()
    }

    /**
     * Método constructor del link
     */
    private fun buildLinkHeader(uri: String, rel: String): String? {
        return "<$uri>; rel=\"$rel\""
    }

    /**
     * Métodos para agregar comás para separar los enlaces
     */
    private fun appendCommaIfNecessary(linkHeader: StringBuilder) {
        if (linkHeader.isNotEmpty()) {
            linkHeader.append(", ")
        }
    }
}
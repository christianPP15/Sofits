package com.sofits.proyectofinal.upload

import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Interfaz que define los métodos para el guardado de imagenes
 */
interface BasicImageStorageService<T, ID, DID> {
    /**
     * Método para guardar el archivo
     * @param file Archivo de tipo multiparte
     */
    fun store(file : MultipartFile) : Optional<T>

    /**
     * Método para cargar un recurso
     * @param id Identificador de la imagen a cargar
     */
    fun loadAsResource(id : ID) : Optional<MediaTypeUrlResource>

    /**
     * Método para eliminar
     * @param id Identificador de la imagen a eliminar
     */
    fun delete(id : DID)


}
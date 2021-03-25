package com.sofits.proyectofinal.Util

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 * Clase para cargar los mensajes de valoración del fichero de properties
 * @author lmlopezmagana
 * @see Configuration
 */
@Configuration
class ConfiguracionValidacion {
    /**
     * Bean que se utiliza para la resolución de mensajes, con soporte para la parametrización e internacionalización de los mensajes.
     */
    @Bean
    fun messageSource() : MessageSource {
        var messageSource = ReloadableResourceBundleMessageSource()

        messageSource.setBasename("classpath:message")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    /**
     * Bean para usar mensajes de nombre personalizados en un archivo de propiedades
     */
    @Bean
    fun getValidator() : LocalValidatorFactoryBean {
        val validator = LocalValidatorFactoryBean()
        validator.setValidationMessageSource(messageSource())
        return validator
    }



}
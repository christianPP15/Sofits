package com.sofits.proyectofinal.Util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

/**
 * Clase que configura y activa la documentación de Swagger 2
 * @author christianPP15
 * @see Configuration
 * @see EnableSwagger2
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {
    /**
     * Bean para iniciar el servicio de Swagger 2
     */
    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
            .apiInfo(ApiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sofits.proyectofinal.Controller"))
            .paths(PathSelectors.any())
            .build()


    /**
     * Bean para configurar la información principal de Swagger 2
     */
    @Bean
    fun ApiInfo() = ApiInfoBuilder().title("Sofits API")
        .description("API de gestión de una aplicación cuyo fin es el intercambio de libros")
        .version("1.0")
        .contact(Contact("Christian Payo Parra","","christianpayo32@gmail.com"))
        .build()

    /**
     * Activa el tipo de seguridad en Swagger
     */
    private fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    /**
     * Da la posibilidad a Swagger 2 de acceder al contexto de la seguridad
     */
    private fun securityContext(): SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth()).build()
    }

    /**
     * Crea la lista de tipos de seguridad que implementa nuestra api
     */
    private fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }


}
package com.sofits.proyectofinal.Util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sofits.proyectofinal.Controller"))
            .paths(PathSelectors.any())
            .build()
        .apiInfo(ApiInfo())

    @Bean
    fun ApiInfo() = ApiInfoBuilder().title("Sofits API")
        .description("API de gestión de una aplicación cuyo fin es el intercambio de libros")
        .version("1.0")
        .contact(Contact("Christian Payo Parra","","christianpayo32@gmail.com"))
        .build()


}
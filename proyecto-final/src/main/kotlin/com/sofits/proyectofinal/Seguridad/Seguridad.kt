package com.sofits.proyectofinal.Seguridad

import com.sofits.proyectofinal.Seguridad.jwt.JwtAuthenticationEntryPoint
import com.sofits.proyectofinal.Seguridad.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Clase anotada con Configuration que configura un bean para encriptar las contraseñas
 * @see Configuration
 * @author lmlopezmagana
 */
@Configuration
class ConfigurePasswordEncoder() {
    /**
     * Función para crear un bean de BCryptPasswordEncoder
     */
    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

}

/**
 * Clase anotada con Configuration que configura un bean para gestionar Cors
 * @see Configuration
 * @author lmlopezmagana
 */
@Configuration
class ConfigureCors() {
    /**
     * Bean de configuración de WebMvcConfigurer dónde configuramos cors
     */
    @Bean
    fun corsConfigurer()  = object : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
        }
    }

}

/**
 * Clase para la configuración de la seguridad
 * @see Configuration
 * @see EnableWebSecurity
 * @see EnableGlobalMethodSecurity
 * @author lmlopezmagana
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val filterChainExceptionHandler : FilterChainExceptionHandler,
    private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    /**
     * Configura un constructor de AuthenticationManagerBuilder
     * @see AuthenticationManagerBuilder
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    /**
     * Configura la seguridad Http, indicando a que zona puede acceder cada usuario, los tipos de seguridad, los filtros que debe pasar...
     * @see HttpSecurity
     */
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/h2-console/**","/v2/swagger.ui.html","/v2/api-docs").permitAll()
            .antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**").permitAll()
                .antMatchers(HttpMethod.POST,"/autores/","/libro/**","/user/book/{userId}/{LibroId}").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT,"/autores/{id}").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE,"/autores/id").hasRole("ADMIN")
            .antMatchers("/autores/").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/auth/login", "/auth/token", "/user/","/auth/register").permitAll()
            .anyRequest().hasRole("USER")



        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(filterChainExceptionHandler, LogoutFilter::class.java)

        http.headers().frameOptions().disable()

        // @formatter:on
    }

    /**
     * Bean de configuración del AuthenticationManager
     * @see AuthenticationManager
     */
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
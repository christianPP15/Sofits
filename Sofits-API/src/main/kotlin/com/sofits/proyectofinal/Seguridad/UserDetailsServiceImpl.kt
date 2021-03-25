package com.sofits.proyectofinal.Seguridad

import com.sofits.proyectofinal.Servicios.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Clase estereotipada como servicio que se encarga de cargar un usuario en base a su email , implementa UserDetailsService
 * @author lmlopezmagana
 * @see UserDetailsService
 */
@Service("userDetailsService")
class UserDetailsServiceImpl() : UserDetailsService {
    /**
     * Servicios del usuario que permite su gestión
     */
    @Autowired
    lateinit var userService: UserService

    /**
     * Carga un usuario en base a su email
     */
    override fun loadUserByUsername(email: String): UserDetails =
        userService.findByEmail(email).orElseThrow {
            UsernameNotFoundException("Usuario $email no encontrado")
        }
}
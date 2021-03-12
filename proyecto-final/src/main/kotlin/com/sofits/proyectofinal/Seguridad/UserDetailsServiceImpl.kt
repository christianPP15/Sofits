package com.sofits.proyectofinal.Seguridad

import com.sofits.proyectofinal.Servicios.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailsServiceImpl() : UserDetailsService {
    @Autowired
    lateinit var userService: UserService

    override fun loadUserByUsername(email: String): UserDetails =
        userService.findByEmail(email).orElseThrow {
            UsernameNotFoundException("Usuario $email no encontrado")
        }
}
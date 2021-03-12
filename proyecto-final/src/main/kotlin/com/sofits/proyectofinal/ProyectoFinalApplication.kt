package com.sofits.proyectofinal

import com.sofits.proyectofinal.Modelos.Usuario
import com.sofits.proyectofinal.Modelos.UsuarioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

@SpringBootApplication
class ProyectoFinalApplication{
	@Bean
	fun init(usuRepo:UsuarioRepository, passwordEncoder: PasswordEncoder): CommandLineRunner {
		return CommandLineRunner {
			val user1= Usuario("christianPP",passwordEncoder.encode("1234"),"Christian Payo Parra", LocalDate.of(2001,9,3),listOf("ADMIN","USER"))
			usuRepo.save(user1)
		}
	}
}

fun main(args: Array<String>) {
	runApplication<ProyectoFinalApplication>(*args)
}

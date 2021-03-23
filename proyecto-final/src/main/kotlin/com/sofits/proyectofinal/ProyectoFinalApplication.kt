package com.sofits.proyectofinal

import com.sofits.proyectofinal.Modelos.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

/**
 * Clase anotada con SpringBootApplication que levanta el contexto de la aplicación
 * @see SpringBootApplication
 */
@SpringBootApplication
class ProyectoFinalApplication{
	/**
	 * Bean para cargar datos de inicio
	 */
	@Bean
	fun init(usuRepo:UsuarioRepository, passwordEncoder: PasswordEncoder,generoLiterarioRepository: GeneroLiterarioRepository,libroRepository: LibroRepository,usuarioTieneLibroRepository: UsuarioTieneLibroRepository,autorRepository: AutorRepository): CommandLineRunner {
		return CommandLineRunner {
			val user1= Usuario("christianPP",passwordEncoder.encode("1234"),"Christian Payo Parra", LocalDate.of(2001,9,3),listOf("ADMIN","USER"),null)
			usuRepo.save(user1)
			val genero=GeneroLiterario("fantasia")
			generoLiterarioRepository.save(genero)
			val autor=Autor("Carlos ruiz","adwdwd", LocalDate.of(1900,3,15))
			autorRepository.save(autor)
			val libro=Libro("El laberito de los espíritus","Libro guapisimo")
			libroRepository.save(libro)
			autor.libros.add(libro)
			libro.autor=autor
			autorRepository.save(autor)
			libroRepository.save(libro)
			val id=UsuarioTieneLibroId(user1.id!!,libro.id!!)
			val libroUsuario=UsuarioTieneLibro(id,user1,libro,"pepe","Buen estado","Ingles",3)
			usuarioTieneLibroRepository.save(libroUsuario)
		}
	}
}
/**
 * Clase que levanta el contexto de la aplicación
 */
fun main(args: Array<String>) {
	runApplication<ProyectoFinalApplication>(*args)
}

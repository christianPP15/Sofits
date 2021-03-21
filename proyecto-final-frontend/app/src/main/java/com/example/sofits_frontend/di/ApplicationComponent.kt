package com.example.sofits_frontend.di

import com.example.sofits_frontend.Api.NetworkModule
import com.example.sofits_frontend.ui.Autores.AutoresDetail.AutorDetailActivity
import com.example.sofits_frontend.ui.Autores.AutoresDetail.LibrosAutor.LibrosAutorDetalleFragment
import com.example.sofits_frontend.ui.Autores.AutoresFragment
import com.example.sofits_frontend.ui.Chat.chatFragment
import com.example.sofits_frontend.ui.Login.LoginActivity
import com.example.sofits_frontend.ui.MiPerfil.AddBook.SelectAutorNewBook
import com.example.sofits_frontend.ui.MiPerfil.Informacion.InformacionUsuarioFragment
import com.example.sofits_frontend.ui.MiPerfil.MisLibrosFragment
import com.example.sofits_frontend.ui.Registro.RegistroActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(registroActivity: RegistroActivity)
    fun inject(misLibros:MisLibrosFragment)
    fun inject(informacionUsuario: InformacionUsuarioFragment)
    fun inject (autoresFragment: AutoresFragment)
    fun inject (autorDetailActivity: AutorDetailActivity)
    fun inject (librosAutorDetalleFragment: LibrosAutorDetalleFragment)
    fun inject (selectAutorNewBook: SelectAutorNewBook)
    fun inject (chatFragment: chatFragment)
}
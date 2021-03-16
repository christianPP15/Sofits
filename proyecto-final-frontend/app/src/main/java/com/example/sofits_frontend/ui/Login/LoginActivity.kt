package com.example.sofits_frontend.ui.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.*
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var respuesta:LoginResponse
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        actionBar?.hide()
        loginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        val botonLogin = findViewById<Button>(R.id.button_login)
        botonLogin.setOnClickListener {
            val user:LoginRequest? = sendLoginRequest()
            if (user!=null){
                loginViewModel.completeLogin(user)
                loginViewModel.loginData.observe(LifecycleOwner, Observer {
                        datosUsuario->
                })
                val shared = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
                with(shared.edit()) {
                    putString(getString(R.string.TOKEN_USER), respuesta.token)
                    putString(getString(R.string.TOKEN_REFRESCO),respuesta.refreshToken)
                    commit()
                }
                val navegar = Intent(this,MainActivity::class.java)
                startActivity(navegar)
            }
        }
    }
    fun sendLoginRequest(): LoginRequest? {
        val emailInput= findViewById<TextView>(R.id.input_email_login)
        val password= findViewById<TextView>(R.id.input_password_login)
        if (emailInput.text.isNotBlank() && password.text.isNotBlank()){
            return LoginRequest(emailInput.text.toString(),password.text.toString())
        }else{
            emailInput.error="Debe introducir un email válido"
            password.error="Debe introducir una contraseña válida"
            return null
        }
    }



}
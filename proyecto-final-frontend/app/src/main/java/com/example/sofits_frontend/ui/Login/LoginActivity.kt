package com.example.sofits_frontend.ui.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.*
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var sofitsService: SofitsRepository= SofitsRepository()
    lateinit var respuesta:LoginResponse
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        actionBar?.hide()
        val botonLogin = findViewById<Button>(R.id.button_login)
        botonLogin.setOnClickListener {
            doLoginComplete()
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

     private fun doLoginComplete(){
        val emailInput= findViewById<TextView>(R.id.input_email_login)
        val password= findViewById<TextView>(R.id.input_password_login)
         if (emailInput.text.isNotBlank() && password.text.isNotBlank()){
             scope.launch {
                 respuesta= doLogin(LoginRequest(emailInput.text.toString(), password.text.toString())).body()
                 println(respuesta)
             }

         }else{
             emailInput.error="Debe introducir un email válido"
             password.error="Debe introducir una contraseña válida"
         }
    }
    suspend fun doLogin(loginRequest: LoginRequest): Response<LoginResponse> = sofitsService.doLogin(loginRequest)

}
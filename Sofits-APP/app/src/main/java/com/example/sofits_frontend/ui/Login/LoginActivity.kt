package com.example.sofits_frontend.ui.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.AuthResponse.LoginResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.Registro.RegistroActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (this.applicationContext as MyApp).appComponent.inject(this)
        supportActionBar?.hide()
        val shared = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
        val token=shared.getString(getString(R.string.TOKEN_USER), "")
        if (token==""){
            val botonLogin = findViewById<Button>(R.id.button_login)
            val textRegistro= findViewById<TextView>(R.id.textView_registro_opcion)
            textRegistro.setOnClickListener {
                var navegarRegistro = Intent(this,RegistroActivity::class.java)
                startActivity(navegarRegistro)
            }
            botonLogin.setOnClickListener {
                val user:LoginRequest? = sendLoginRequest()
                if (user!=null){
                    var loginData: LoginResponse?
                    loginViewModel.doLoginComplete(user)
                    loginViewModel.loginData.observe(this, Observer { response->
                        when(response){
                            is Resource.Success ->{
                                loginData= response.data
                                if (loginData!=null){
                                    val shared = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
                                    with(shared.edit()) {
                                        putString(getString(R.string.TOKEN_USER), loginData!!.token)
                                        putString(getString(R.string.TOKEN_REFRESCO),loginData!!.refreshToken)
                                        putString(getString(R.string.IdentificadorUsuario),loginData!!.user.id)
                                        putString(getString(R.string.rolesUsuario),loginData!!.user.roles)
                                        commit()
                                    }
                                    findViewById<EditText>(R.id.input_email_login).text.clear()
                                    findViewById<EditText>(R.id.input_password_login).text.clear()
                                    val navegar = Intent(this,MainActivity::class.java)
                                    startActivity(navegar)
                                }
                            }
                            is Resource.Error ->{
                                if (response.message=="Bad credentials"){
                                    findViewById<TextView>(R.id.textView_mensajeErrorLogin).visibility=TextView.VISIBLE
                                }else{
                                    val toast= Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG)
                                    toast.show()
                                }
                            }
                        }

                    })

                }
            }
        }else{
            val navegar = Intent(this,MainActivity::class.java)
            startActivity(navegar)
        }
    }
    fun sendLoginRequest(): LoginRequest? {
        val emailInput= findViewById<EditText>(R.id.input_email_login)
        val passwordInput= findViewById<EditText>(R.id.input_password_login)
        if (emailInput.text.isNotBlank() && passwordInput.text.isNotBlank()){
            return LoginRequest(emailInput.text.toString(),passwordInput.text.toString())
        }else{
            emailInput.error="Debe introducir un email válido"
            passwordInput.error="Debe introducir una contraseña válida"
            return null
        }
    }



}
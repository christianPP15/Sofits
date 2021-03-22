package com.example.sofits_frontend.ui.Registro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.MainActivity
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import com.example.sofits_frontend.ui.Login.LoginViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RegistroActivity : AppCompatActivity() {
    var uri:Uri?= null
    var PICK_IMAGEN_COUNT=0
    @Inject lateinit var registerViewModel: RegistroViewModel
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_registro)

        var choose : Button = findViewById(R.id.button_choose_Imagen)
        choose.setOnClickListener {
            pickImage()
            val toast= Toast.makeText(applicationContext,uri.toString(),Toast.LENGTH_LONG)
            toast.show()
        }
        val botonRegistro= findViewById<Button>(R.id.buton_register)
        botonRegistro.setOnClickListener {
            val registerData:RegisterRequest?= sendRegisterInfo()
            if(registerData!=null){
                var registerDataResponse: RegisterResponse?
                registerViewModel.doRegisterComplete(registerData)
                registerViewModel.registerData.observe(this, Observer { response->
                    when(response) {
                        is Resource.Success -> {
                            registerDataResponse=response.data
                            if (registerDataResponse!=null){
                                val user=hashMapOf(
                                    "nombre" to registerDataResponse!!.user.nombre
                                )
                                db.collection("users")
                                    .document(registerDataResponse!!.user.id)
                                    .set(user)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d("NEWUSERFIREBASE", "DocumentSnapshot added correctly")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("NEWUSERFIREBASE", "Error adding document", e)
                                    }
                                val shared = getSharedPreferences(getString(R.string.TOKEN), Context.MODE_PRIVATE)
                                with(shared.edit()) {
                                    putString(getString(R.string.TOKEN_USER), registerDataResponse!!.token)
                                    putString(getString(R.string.TOKEN_REFRESCO),registerDataResponse!!.refreshToken)
                                    putString(getString(R.string.IdentificadorUsuario),registerDataResponse!!.user.id)
                                    commit()
                                }
                                val navegar = Intent(this, MainActivity::class.java)
                                startActivity(navegar)
                            }
                        }
                        is Resource.Error -> {
                            val toast= Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG)
                            toast.show()
                        }
                    }
                })
            }
        }
    }


    private fun sendRegisterInfo(): RegisterRequest? {
        val emailInput = findViewById<TextView>(R.id.input_register_email)
        val nombreUsuario = findViewById<TextView>(R.id.input_nombre_registro)
        val password = findViewById<TextView>(R.id.input_password_register)
        val passwordRepeat= findViewById<TextView>(R.id.input_password_repeat_register)
        val fecha= findViewById<TextView>(R.id.input_fecha_registro)
        if (emailInput.text.isNotBlank() && nombreUsuario.text.isNotBlank() && password.text.isNotBlank() && passwordRepeat.text.isNotBlank() && fecha.text.isNotBlank()){
            if (password.text.toString()==passwordRepeat.text.toString()){
                return RegisterRequest(emailInput.text.toString(),
                    nombreUsuario.text.toString(),password.text.toString(),
                    passwordRepeat.text.toString(),fecha.text.toString())
            }else{
                password.error="Las contraseñas deben ser iguales"
                return null
            }
        }else{
            if( emailInput.text.isEmpty()) emailInput.error="Introduce un email válido"
            if (nombreUsuario.text.isEmpty()) nombreUsuario.error="Introduzca un nombre de usuario"
            if (password.text.isEmpty()) password.error="Introduzca una contraseña válida"
            if (passwordRepeat.text.isEmpty())passwordRepeat.error="Introduzca una contraseña válida"
            if (fecha.text.isEmpty()) fecha.error="Introduce una fecha válida"
            return null
        }

    }
    private fun pickImage(){
        val intent = Intent()
        intent.type="image/*"
        intent.putExtra(Intent.ACTION_PICK,true)
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagen"),PICK_IMAGEN_COUNT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGEN_COUNT){
            if (resultCode==Activity.RESULT_OK){
                val uridata = data!!.data
                uri= uridata!!
            }
        }
    }
}
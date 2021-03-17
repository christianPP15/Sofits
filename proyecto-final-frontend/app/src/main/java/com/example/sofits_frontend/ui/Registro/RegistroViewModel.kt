package com.example.sofits_frontend.ui.Registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.Api.response.RegisterResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistroViewModel : ViewModel(){
    private var sofitsRepository: SofitsRepository = SofitsRepository()
    var RegisterResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerData: LiveData<Resource<RegisterResponse>>
        get() = RegisterResponse

    fun doRegisterComplete(registerRequest: RegisterRequest)=  viewModelScope.launch {
        RegisterResponse.value= Resource.Loading()
        val respuesta = sofitsRepository.registrarse(registerRequest)
        RegisterResponse.value = handleRegisterResponse(respuesta)
    }

    private fun handleRegisterResponse(respuesta: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it)
            }
        }
        return Resource.Error("Error en la petición")
    }
}
package com.example.sofits_frontend.ui.Registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class RegistroViewModel @Inject constructor(private var sofitsRepository: SofitsRepository) : ViewModel(){

    var RegisterResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerData: LiveData<Resource<RegisterResponse>>
        get() = RegisterResponse

    fun doRegisterComplete(registerRequest: RegisterRequest)=  viewModelScope.launch {
        RegisterResponse.value= Resource.Loading()
        //val respuesta = sofitsRepository.registrarse(,registerRequest)
        //RegisterResponse.value = handleRegisterResponse(respuesta)
    }

    private fun handleRegisterResponse(respuesta: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
}
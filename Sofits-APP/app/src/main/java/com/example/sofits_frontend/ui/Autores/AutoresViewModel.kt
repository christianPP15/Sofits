package com.example.sofits_frontend.ui.Autores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AuthResponse.LoginResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AutoresViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){

    var autoresResponse: MutableLiveData<Resource<AutoresResponse>> = MutableLiveData()
    init {
        getAutores()
    }

    val AutoresData: LiveData<Resource<AutoresResponse>>
        get() = autoresResponse


    fun getAutores() = viewModelScope.launch {
        autoresResponse.value=Resource.Loading()
        val respuesta = sofitsRepository.getAutores()
        autoresResponse.value=handleLoginResponse(respuesta)
    }

    private fun handleLoginResponse(respuesta: Response<AutoresResponse>): Resource<AutoresResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }

}
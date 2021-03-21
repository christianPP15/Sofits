package com.example.sofits_frontend.ui.MiPerfil.AddBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AddBookViewModel @Inject constructor(val sofitsRepository: SofitsRepository): ViewModel(){


    var autoresResponse: MutableLiveData<Resource<AutoresResponse>> = MutableLiveData()

    val MyInfoData: LiveData<Resource<AutoresResponse>>
        get() = autoresResponse

    fun cargarAutores() = viewModelScope.launch {
        autoresResponse.value=Resource.Loading()
        val result = sofitsRepository.getAutores()
        autoresResponse.value=handleLoginResponse(result)
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
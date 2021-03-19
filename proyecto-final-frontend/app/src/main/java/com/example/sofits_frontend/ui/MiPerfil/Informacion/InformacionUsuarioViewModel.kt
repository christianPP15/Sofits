package com.example.sofits_frontend.ui.MiPerfil.Informacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones.MisValoracionesResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class InformacionUsuarioViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){

    var inforResponse: MutableLiveData<Resource<MisValoracionesResponse>> = MutableLiveData()
    val infoData: LiveData<Resource<MisValoracionesResponse>>
        get() = inforResponse

    init {
        getMisValoraciones()
    }

    fun getMisValoraciones()= viewModelScope.launch {
        inforResponse.value = Resource.Loading()
        val respuesta = sofitsRepository.getMisValoraciones()
        inforResponse.value= handleLoginResponse(respuesta)
    }
    private fun handleLoginResponse(respuesta: Response<MisValoracionesResponse>): Resource<MisValoracionesResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
}
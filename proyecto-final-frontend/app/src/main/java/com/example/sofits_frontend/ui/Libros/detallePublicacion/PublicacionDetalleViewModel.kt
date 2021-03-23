package com.example.sofits_frontend.ui.Libros.detallePublicacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.PublicacionesResponse.PublicacionesResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.detalles.DetallePublicacionResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class PublicacionDetalleViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){

    var publicacionResponse: MutableLiveData<Resource<DetallePublicacionResponse>> = MutableLiveData()
    val publicacionData: LiveData<Resource<DetallePublicacionResponse>>
        get() = publicacionResponse

    fun getPublicaciones(idLibro: String,idUsuario:String) = viewModelScope.launch {
        publicacionResponse.value= Resource.Loading()
        val result = sofitsRepository.getDetailPublicacion(idLibro,idUsuario)
        publicacionResponse.value = handleLoginResponse(result)
    }
    private fun handleLoginResponse(respuesta: Response<DetallePublicacionResponse>): Resource<DetallePublicacionResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
    fun changeState(idLibro: String)= viewModelScope.launch {
        sofitsRepository.changeState(idLibro)
    }
    fun eliminarPublicacion(id:String)= viewModelScope.launch {
        sofitsRepository.eliminarPublicacion(id)
    }
}
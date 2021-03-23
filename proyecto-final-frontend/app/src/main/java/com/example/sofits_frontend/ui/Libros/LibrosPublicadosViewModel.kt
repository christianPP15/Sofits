package com.example.sofits_frontend.ui.Libros

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.AutorDetailResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.PublicacionesResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class LibrosPublicadosViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){

    var publicacionResponse: MutableLiveData<Resource<PublicacionesResponse>> = MutableLiveData()
    val publicacionesData: LiveData<Resource<PublicacionesResponse>>
        get() = publicacionResponse

    fun getPublicaciones(id: String) = viewModelScope.launch {
        publicacionResponse.value= Resource.Loading()
        val result = sofitsRepository.getAllPublishedBook(id)
        publicacionResponse.value = handleLoginResponse(result)
    }
    private fun handleLoginResponse(respuesta: Response<PublicacionesResponse>): Resource<PublicacionesResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }

    fun addMeGustaLibro(id: String)=viewModelScope.launch {
        sofitsRepository.addMeGustaLibro(id)
    }

    fun removeMeGustaLibro(id:String) = viewModelScope.launch {
        sofitsRepository.removeMeGustaLibro(id)
    }

}
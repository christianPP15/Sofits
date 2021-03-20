package com.example.sofits_frontend.ui.Autores.AutoresDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.AutorDetailResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AutoresDetailsViewModel  @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){

    var autorResponse: MutableLiveData<Resource<AutorDetailResponse>> = MutableLiveData()
    val AutoresData: LiveData<Resource<AutorDetailResponse>>
        get() = autorResponse



    fun getAutorInfo(id: String) = viewModelScope.launch {
        autorResponse.value= Resource.Loading()
        val result = sofitsRepository.getAutorById(id)
        autorResponse.value = handleLoginResponse(result)
        println(autorResponse.value)
    }

    fun MeGustaAutor(id:String) = viewModelScope.launch {
        sofitsRepository.addAutorMeGusta(id)
    }

    fun RemoveMeGusta(id: String) = viewModelScope.launch {
        sofitsRepository.removeMeGustaAutor(id)
    }
    private fun handleLoginResponse(respuesta: Response<AutorDetailResponse>): Resource<AutorDetailResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }

}
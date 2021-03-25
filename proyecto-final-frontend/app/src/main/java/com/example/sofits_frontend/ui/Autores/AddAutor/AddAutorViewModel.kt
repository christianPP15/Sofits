package com.example.sofits_frontend.ui.Autores.AddAutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.AddAutor.AddAutorResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AddAutorViewModel @Inject constructor(var sofitsRepository: SofitsRepository)  : ViewModel(){

    var autorResponse: MutableLiveData<Resource<AddAutorResponse>> = MutableLiveData()
    val autorData: LiveData<Resource<AddAutorResponse>>
        get() = autorResponse
    fun addAutor(file: MultipartBody.Part, newAutor: RequestBody)= viewModelScope.launch {
     autorResponse.value=Resource.Loading()
        val result = sofitsRepository.addAutor(file,newAutor)
        autorResponse.value = handleAddAutorResponse(result)
    }
    private fun handleAddAutorResponse(respuesta: Response<AddAutorResponse>): Resource<AddAutorResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
}
package com.example.sofits_frontend.ui.MiPerfil.AddBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.EditBook
import com.example.sofits_frontend.Api.request.NuevoEjemplarRequest
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.addEjemplar.NuevoEjemplarResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AddBookViewModel @Inject constructor(val sofitsRepository: SofitsRepository): ViewModel(){


    var autoresResponse: MutableLiveData<Resource<AutoresResponse>> = MutableLiveData()

    val MyInfoData: LiveData<Resource<AutoresResponse>>
        get() = autoresResponse

    val nuevoLibro : MutableLiveData<Resource<NuevoEjemplarResponse>> = MutableLiveData()

    val InfoNewLibro : LiveData<Resource<NuevoEjemplarResponse>>
        get() = nuevoLibro

    fun cargarAutores() = viewModelScope.launch {
        autoresResponse.value=Resource.Loading()
        val result = sofitsRepository.getAutores()
        autoresResponse.value=handleAutoresResponse(result)
    }
    private fun handleAutoresResponse(respuesta: Response<AutoresResponse>): Resource<AutoresResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
    fun editarLibro(id:String,bookEdited:EditBook)= viewModelScope.launch {
        sofitsRepository.editarLibro(id,bookEdited)
    }

    fun addNewBook(id: String,file: MultipartBody.Part, newBook:RequestBody)= viewModelScope.launch {
        nuevoLibro.value= Resource.Loading()
        val result=sofitsRepository.addEjemplar(id,file,newBook)
        nuevoLibro.value=handleNuevoEjemplarRequestResponse(result)
    }
    private fun handleNuevoEjemplarRequestResponse(respuesta: Response<NuevoEjemplarResponse>): Resource<NuevoEjemplarResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }



}
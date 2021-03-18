package com.example.sofits_frontend.ui.MiPerfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MiPerfilResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MisLibrosViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel(){


    var MyInfoResponse: MutableLiveData<Resource<MiPerfilResponse>> = MutableLiveData()

    val MyInfoData: LiveData<Resource<MiPerfilResponse>>
        get() = MyInfoResponse

    init {
        getMyInfo()
    }

    fun getMyInfo() = viewModelScope.launch{
        MyInfoResponse.value= Resource.Loading()
        val respuesta = sofitsRepository.getMyProfilesInfo()
        MyInfoResponse.value = handleLoginResponse(respuesta)
    }

    private fun handleLoginResponse(respuesta: Response<MiPerfilResponse>): Resource<MiPerfilResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }
}
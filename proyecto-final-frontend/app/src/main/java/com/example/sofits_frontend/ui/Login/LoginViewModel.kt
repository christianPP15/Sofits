package com.example.sofits_frontend.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class LoginViewModel @Inject constructor(val sofitsRepository: SofitsRepository) : ViewModel() {

    var loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginData: LiveData<Resource<LoginResponse>>
        get() = loginResponse

    fun doLoginComplete(loginRequest: LoginRequest)=  viewModelScope.launch {
        loginResponse.value=Resource.Loading()
        val respuesta = sofitsRepository.loaguearte(loginRequest)
        loginResponse.value = handleLoginResponse(respuesta)
    }

    private fun handleLoginResponse(respuesta: Response<LoginResponse>): Resource<LoginResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it!!)
            }
        }
        val error : ApiError = sofitsRepository.parseError(respuesta)
        return Resource.Error(error.mensaje)
    }

}
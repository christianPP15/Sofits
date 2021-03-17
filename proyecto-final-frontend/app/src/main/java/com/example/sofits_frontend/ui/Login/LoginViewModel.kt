package com.example.sofits_frontend.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.Resource
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private var sofitsRepository: SofitsRepository = SofitsRepository()
    var loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginData: LiveData<Resource<LoginResponse>>
        get() = loginResponse

    fun doLoginComplete(loginRequest: LoginRequest)=  viewModelScope.launch {
        loginResponse.value=Resource.Loading()
        val respuesta = sofitsRepository.loaguearte(loginRequest)
        loginResponse.value = handleLoginResponse(respuesta)
        println(loginResponse.value.toString())
    }

    private fun handleLoginResponse(respuesta: Response<LoginResponse>): Resource<LoginResponse> {
        if (respuesta.isSuccessful){
            respuesta.body().let {
                return Resource.Success(it)
            }
        }
        return Resource.Error("Error en la petición")
    }

}
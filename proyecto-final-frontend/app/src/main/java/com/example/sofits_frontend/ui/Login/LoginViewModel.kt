package com.example.sofits_frontend.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private var sofitsRepository: SofitsRepository = SofitsRepository()
    var loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginData: LiveData<LoginResponse>
        get() = loginResponse

    fun doLoginComplete(loginRequest: LoginRequest)=  viewModelScope.launch {
        val respuesta = sofitsRepository.loaguearte(loginRequest)
        loginResponse.value = respuesta.body()
        println(loginResponse.value.toString())
    }

}
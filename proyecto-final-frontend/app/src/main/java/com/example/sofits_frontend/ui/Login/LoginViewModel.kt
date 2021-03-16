package com.example.sofits_frontend.ui.Login

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.repository.SofitsRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private var sofitsService: SofitsRepository = SofitsRepository()
    var loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginData: LiveData<LoginResponse>
        get() = loginResponse

    private fun doLoginComplete(loginRequest: LoginRequest): Response<LoginResponse>? {
        var response: Response<LoginResponse> ? =null
        viewModelScope.launch {
           response =sofitsService.doLogin(loginRequest)
        }
        return response
    }

     fun completeLogin(loginRequest: LoginRequest){
        val result=doLoginComplete(loginRequest)
        loginResponse.value=result?.body()
    }
}
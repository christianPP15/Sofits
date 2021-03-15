package com.example.sofits_frontend.repository

import com.example.sofits_frontend.Api.SofitsService
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.common.MyApp
import retrofit2.Response

class SofitsRepository {

    private val sofitsService = MyApp.networkContainer.sofitsService

    suspend fun doLogin(loginRequest: LoginRequest) : Response<LoginResponse> = sofitsService.doLogin(loginRequest)
}
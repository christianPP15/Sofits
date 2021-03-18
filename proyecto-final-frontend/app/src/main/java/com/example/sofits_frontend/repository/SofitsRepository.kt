package com.example.sofits_frontend.repository

import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.Api.response.RegisterResponse
import com.example.sofits_frontend.common.MyApp
import org.json.JSONObject
import retrofit2.Response

class SofitsRepository {

    private val sofitsService = MyApp.networkContainer.sofitsService

    suspend fun loaguearte(loginRequest: LoginRequest) : Response<LoginResponse> = sofitsService.doLogin(loginRequest)

    suspend fun registrarse(registerRequest: RegisterRequest) : Response<RegisterResponse> = sofitsService.doRegister(registerRequest)

    fun parseError(response:Response<*>): ApiError{
        val jsonObject = JSONObject(response.errorBody()?.string())
        return ApiError(jsonObject.getString("estado"),jsonObject.getString("fecha"),jsonObject.getString("mensaje"))
    }
}
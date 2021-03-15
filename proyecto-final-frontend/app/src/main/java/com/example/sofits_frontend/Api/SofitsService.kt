package com.example.sofits_frontend.Api

import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SofitsService {

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>
}
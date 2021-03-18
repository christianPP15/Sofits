package com.example.sofits_frontend.Api

import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.LoginResponse
import com.example.sofits_frontend.Api.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SofitsService {

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("auth/register")
    suspend fun doRegister(@Part("newUser") registerRequest: RegisterRequest) : Response<RegisterResponse>
    /*@Multipart
    @POST("auth/register")
    suspend fun doRegister(
        @Part("newUser") registerRequest: RegisterRequest,
        @Part MultipartBody.Part file
     ) : Response<RegisterResponse>*/
}
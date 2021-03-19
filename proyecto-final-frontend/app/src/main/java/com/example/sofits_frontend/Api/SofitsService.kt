package com.example.sofits_frontend.Api

import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.LoginResponse
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones.MisValoracionesResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SofitsService {

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    /*@POST("auth/register")
    suspend fun doRegister(@Part("newUser") registerRequest: RegisterRequest) : Response<RegisterResponse>*/
    @Multipart
    @POST("auth/register")
    suspend fun doRegister(@Part("file") file:RequestBody , @Part("newUser") registerRequest: RegisterRequest) : Response<RegisterResponse>

    @GET("user/book/")
    suspend fun getMyBooks() : Response<MiPerfilResponse>

    @GET("valoraciones/me")
    suspend fun getMisValoraciones() : Response<MisValoracionesResponse>

    @GET("autores/")
    suspend fun getAutores() : Response<AutoresResponse>
}
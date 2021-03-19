package com.example.sofits_frontend.repository

import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.SofitsService
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.LoginResponse
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones.MisValoracionesResponse
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class SofitsRepository @Inject constructor(@Named("sofitServiceWithoutInterceptor") val sofitsService: SofitsService,
                                           @Named("sofitServiceInterceptor") val sofitsServiceConToken :SofitsService  ){

    suspend fun loaguearte(loginRequest: LoginRequest) : Response<LoginResponse> = sofitsService.doLogin(loginRequest)

    suspend fun registrarse(file:RequestBody,registerRequest: RegisterRequest) : Response<RegisterResponse> = sofitsService.doRegister(file,registerRequest)

    suspend fun getMyProfilesInfo() : Response<MiPerfilResponse> = sofitsServiceConToken.getMyBooks()

    suspend fun getMisValoraciones() : Response<MisValoracionesResponse> = sofitsServiceConToken.getMisValoraciones()

    suspend fun getAutores() : Response<AutoresResponse> = sofitsServiceConToken.getAutores()

    fun parseError(response:Response<*>): ApiError{
        val jsonObject = JSONObject(response.errorBody()?.string())
        return ApiError(jsonObject.getString("estado"),jsonObject.getString("fecha"),jsonObject.getString("mensaje"))
    }
}
package com.example.sofits_frontend.Api

import com.example.sofits_frontend.common.Constantes
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkContainer {

    private val SofitsInterceptor:SofitsInterceptor= SofitsInterceptor()
    private val okhttpCliente: OkHttpClient = with(
        OkHttpClient.Builder()){
        addInterceptor(SofitsInterceptor)
        build()
    }

    val sofitsService : SofitsService = Retrofit.Builder()
        .baseUrl(Constantes.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(SofitsService::class.java)

    val sofitsServiceInterceptor : SofitsService = Retrofit.Builder()
        .baseUrl(Constantes.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpCliente)
        .build()
        .create(SofitsService::class.java)
}
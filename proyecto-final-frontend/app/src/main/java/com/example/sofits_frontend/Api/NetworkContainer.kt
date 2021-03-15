package com.example.sofits_frontend.Api

import com.example.sofits_frontend.common.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkContainer {

    val sofitsService : SofitsService = Retrofit.Builder().
            baseUrl(Constantes.API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build().create(SofitsService::class.java)
}
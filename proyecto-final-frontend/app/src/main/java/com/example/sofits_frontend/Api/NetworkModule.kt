package com.example.sofits_frontend.Api

import com.example.sofits_frontend.common.Constantes
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule (){

    @Singleton
    @Provides
    @Named("apiUrl")
    fun provideBaseUrl() : String = Constantes.API_BASE_URL

    @Singleton
    @Provides
    @Named("imageUrl")
    fun provideBaseImageUrl() : String = Constantes.imageURL

    @Singleton
    @Provides
    fun provideOkHttpClient(sofitsInterceptor: SofitsInterceptor):OkHttpClient = with(
            OkHttpClient.Builder()){
            addInterceptor(sofitsInterceptor)
            build()
    }

    @Singleton
    @Provides
    @Named("sofitServiceInterceptor")
    fun provideTheSofitsService(@Named("apiUrl") url:String,okHttpClient: OkHttpClient): SofitsService = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SofitsService::class.java)

    @Singleton
    @Provides
    @Named("sofitServiceWithoutInterceptor")
    fun provideTheSofitsServiceWithoutInterceptor(@Named("apiUrl") url:String) : SofitsService = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(SofitsService::class.java)


}
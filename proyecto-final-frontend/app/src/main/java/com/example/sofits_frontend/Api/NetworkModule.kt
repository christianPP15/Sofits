package com.example.sofits_frontend.Api

import com.example.sofits_frontend.common.Constantes
import com.example.sofits_frontend.repository.SofitsRepository
import com.example.sofits_frontend.ui.Autores.AutoresDetail.AutoresDetailsViewModel
import com.example.sofits_frontend.ui.Libros.LibrosPublicadosViewModel
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
    fun provideOkHttpClient(sofitsInterceptor: SofitsInterceptor/*,loggingInterceptor: HttpLoggingInterceptor*/):OkHttpClient {
        //loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
       return with(
            OkHttpClient.Builder()){
            addInterceptor(sofitsInterceptor)
            //addInterceptor(loggingInterceptor)
            build()
        }
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
    fun provideTheSofitsServiceWithoutInterceptor(@Named("apiUrl") url:String) : SofitsService {
        val logging = HttpLoggingInterceptor()
// set your desired log level
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
// add your other interceptors …

// add logging as last interceptor
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- th

        return  Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SofitsService::class.java)
    }

    @Singleton
    @Provides
    @Named("provideRepository")
    fun provideRepository(@Named("sofitServiceWithoutInterceptor") sofitsService: SofitsService, @Named("sofitServiceInterceptor") sofitsServiceConToken: SofitsService) = SofitsRepository(sofitsService, sofitsServiceConToken)

    @Singleton
    @Provides
    @Named("provideAutoresDetailsViewModel")
    fun provideAutoresDetailsViewModel(@Named("provideRepository") softRepository: SofitsRepository): AutoresDetailsViewModel = AutoresDetailsViewModel(softRepository)

    @Singleton
    @Provides
    @Named("providePublicacionesViewModel")
    fun providePublicacionesViewModel(@Named("provideRepository") softRepository: SofitsRepository) : LibrosPublicadosViewModel = LibrosPublicadosViewModel(softRepository)
}
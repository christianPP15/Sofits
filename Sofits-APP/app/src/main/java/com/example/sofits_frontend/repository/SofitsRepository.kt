package com.example.sofits_frontend.repository

import com.example.sofits_frontend.Api.ApiError
import com.example.sofits_frontend.Api.NoContent
import com.example.sofits_frontend.Api.SofitsService
import com.example.sofits_frontend.Api.request.EditBook
import com.example.sofits_frontend.Api.request.LoginRequest
import com.example.sofits_frontend.Api.request.RegisterRequest
import com.example.sofits_frontend.Api.response.AuthResponse.LoginResponse
import com.example.sofits_frontend.Api.response.AuthResponse.RegisterResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.AutoresResponse
import com.example.sofits_frontend.Api.response.AutoresResponse.DetailAutor.AutorDetailResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisLibros.MiPerfilResponse
import com.example.sofits_frontend.Api.response.MiPerfilResponse.MisValoraciones.MisValoracionesResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.PublicacionesResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.detalles.DetallePublicacionResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.meGustaLibro.AddMeGustaLibroResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class SofitsRepository @Inject constructor(@Named("sofitServiceWithoutInterceptor") val sofitsService: SofitsService,
                                           @Named("sofitServiceInterceptor") val sofitsServiceConToken :SofitsService  ){

    suspend fun loaguearte(loginRequest: LoginRequest) : Response<LoginResponse> = sofitsService.doLogin(loginRequest)

    suspend fun registrarse(file: MultipartBody.Part, registerRequest: RequestBody) : Response<RegisterResponse> = sofitsService.doRegister(file,registerRequest)

    suspend fun getMyProfilesInfo() : Response<MiPerfilResponse> = sofitsServiceConToken.getMyBooks()

    suspend fun getMisValoraciones() : Response<MisValoracionesResponse> = sofitsServiceConToken.getMisValoraciones()

    suspend fun getAutores() : Response<AutoresResponse> = sofitsServiceConToken.getAutores()

    suspend fun addAutorMeGusta(id:String) : Response<AutorDetailResponse> = sofitsServiceConToken.AddMeGustaAutor(id)

    suspend fun removeMeGustaAutor(id: String) : Response<NoContent> = sofitsServiceConToken.RemoveMeGustaAutor(id)

    suspend fun getAutorById(id: String) : Response<AutorDetailResponse> = sofitsServiceConToken.GetAutorById(id)

    suspend fun getAllPublishedBook(id: String) : Response<PublicacionesResponse> = sofitsServiceConToken.GetBookPublished(id)

    suspend fun addMeGustaLibro(id: String) : Response<AddMeGustaLibroResponse> = sofitsServiceConToken.addMeGustaLibro(id)

    suspend fun removeMeGustaLibro(id: String) : Response<NoContent> = sofitsServiceConToken.removeMeGustaLibro(id)

    suspend fun getDetailPublicacion(idLibro:String,idUsuario:String) : Response<DetallePublicacionResponse> = sofitsServiceConToken.getDetailPublicacion(idLibro,idUsuario)

    suspend fun changeState(idLibro: String): Response<NoContent> = sofitsServiceConToken.marcarLibroComoIntercambiado(idLibro)

    suspend fun eliminarPublicacion(idLibro: String) : Response<NoContent> = sofitsServiceConToken.eliminarPublicacion(idLibro)

    suspend fun editarLibro(id: String,editBook: EditBook) : Response<NoContent> = sofitsServiceConToken.editarLibro(id,editBook)

    suspend fun addEjemplar(id: String, file: MultipartBody.Part, newBook:RequestBody) = sofitsServiceConToken.addEjemplar(id,file,newBook)

    suspend fun addAutor(file: MultipartBody.Part, newAutor:RequestBody) = sofitsServiceConToken.addAutor(file,newAutor)

    fun parseError(response:Response<*>): ApiError{
        val jsonObject = JSONObject(response.errorBody()?.string())
        return ApiError(jsonObject.getString("estado"),jsonObject.getString("fecha"),jsonObject.getString("mensaje"))
    }
}
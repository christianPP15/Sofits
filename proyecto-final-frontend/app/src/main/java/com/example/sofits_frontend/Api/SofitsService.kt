package com.example.sofits_frontend.Api

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
import com.example.sofits_frontend.Api.response.PublicacionesResponse.addEjemplar.NuevoEjemplarResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.detalles.DetallePublicacionResponse
import com.example.sofits_frontend.Api.response.PublicacionesResponse.meGustaLibro.AddMeGustaLibroResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SofitsService {

    @POST("auth/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @Multipart
    @POST("auth/register")
    suspend fun doRegister(@Part file:MultipartBody.Part , @Part("newUser") registerRequest: RequestBody) : Response<RegisterResponse>

    @GET("user/book/")
    suspend fun getMyBooks() : Response<MiPerfilResponse>

    @GET("valoraciones/me")
    suspend fun getMisValoraciones() : Response<MisValoracionesResponse>

    @GET("autores/")
    suspend fun getAutores() : Response<AutoresResponse>

    @POST("user/fav/autor/{id}")
    suspend fun AddMeGustaAutor(@Path("id") autorID:String) : Response<AutorDetailResponse>

    @DELETE("user/fav/autor/{id}")
    suspend fun RemoveMeGustaAutor(@Path("id") autorID: String) : Response<NoContent>

    @GET("autores/{id}")
    suspend fun GetAutorById(@Path("id") idAutor:String) : Response<AutorDetailResponse>

    @GET("user/book/{id}")
    suspend fun GetBookPublished(@Path("id") idLibro:String) : Response<PublicacionesResponse>

    @POST("user/fav/libro/{id}")
    suspend fun addMeGustaLibro(@Path("id") id:String) : Response<AddMeGustaLibroResponse>

    @DELETE("user/fav/libro/{id}")
    suspend fun removeMeGustaLibro(@Path("id") id: String) : Response<NoContent>

    @GET("user/book/{idLibro}/{idUsuario}")
    suspend fun getDetailPublicacion(@Path("idLibro") idLibro:String,@Path("idUsuario") idUsuario:String) : Response<DetallePublicacionResponse>

    @PUT("user/book/{id}/estado")
    suspend fun marcarLibroComoIntercambiado(@Path("id") idLibro:String) : Response<NoContent>

    @DELETE("user/book/{id}")
    suspend fun eliminarPublicacion(@Path("id") idLibro: String) :Response<NoContent>

    @PUT("user/book/{id}")
    suspend fun editarLibro(@Path("id") id: String,@Body libroEditado:EditBook) : Response<NoContent>

    @Multipart
    @POST("user/book/{id}")
    suspend fun addEjemplar(@Path("id") idLibro: String,@Part file:MultipartBody.Part , @Part("libro") registerRequest: RequestBody) : Response<NuevoEjemplarResponse>
}
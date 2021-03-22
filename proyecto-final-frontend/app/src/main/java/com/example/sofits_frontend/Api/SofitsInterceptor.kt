package com.example.sofits_frontend.Api

import android.content.Context
import android.util.Log
import com.example.sofits_frontend.R
import com.example.sofits_frontend.common.MyApp
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SofitsInterceptor @Inject constructor(): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val shared = MyApp.instance.getSharedPreferences(MyApp.instance.getString(R.string.TOKEN), Context.MODE_PRIVATE)

        val token = shared.getString(MyApp.instance.getString(R.string.TOKEN_USER), "")
        val original = chain.request()
        return if (token!=""){
            val requestBuilder:Request.Builder = original.newBuilder().addHeader("Authorization",
                "Bearer $token"
            )
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }else{
            chain.proceed(original)
        }
    }

}
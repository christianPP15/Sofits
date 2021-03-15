package com.example.sofits_frontend.common

import android.app.Application
import com.example.sofits_frontend.Api.NetworkContainer

class MyApp : Application(){
    companion object{
        lateinit var instance: MyApp
        val networkContainer= NetworkContainer()
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}
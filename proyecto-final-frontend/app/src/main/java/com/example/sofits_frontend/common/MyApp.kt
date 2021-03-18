package com.example.sofits_frontend.common

import android.app.Application
import com.example.sofits_frontend.Api.NetworkModule
import com.example.sofits_frontend.di.ApplicationComponent
import com.example.sofits_frontend.di.DaggerApplicationComponent

class MyApp : Application(){

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
    companion object{
        lateinit var instance: MyApp
        val networkContainer= NetworkModule()
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}
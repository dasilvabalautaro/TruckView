package com.pingpongpacket.truckview

import android.app.Application
import com.pingpongpacket.truckview.dagger.AppComponent
import com.pingpongpacket.truckview.dagger.AppModule
import com.pingpongpacket.truckview.dagger.DaggerAppComponent


class App: Application() {
    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent =  DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
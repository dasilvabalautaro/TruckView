package com.pingpongpacket.truckview

import android.app.Application
import com.pingpongpacket.truckview.dagger.AppComponent
import com.pingpongpacket.truckview.dagger.AppModule
import com.pingpongpacket.truckview.dagger.DaggerAppComponent

class App: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }


}
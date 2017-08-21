package com.pingpongpacket.truckview

import android.app.Application
import android.content.res.Configuration
import com.pingpongpacket.truckview.dagger.AppComponent
import com.pingpongpacket.truckview.dagger.AppModule
import com.pingpongpacket.truckview.dagger.DaggerAppComponent
import com.pingpongpacket.truckview.tools.LocaleUtils
import java.util.*
import javax.inject.Inject

class App: Application() {

    @Inject
    lateinit var localeUtils: LocaleUtils

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        localeUtils.setLocale(Locale("es"))
        localeUtils.updateConfiguration(this, baseContext.resources.configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        localeUtils.updateConfiguration(this, newConfig!!)
    }




}
package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.presentation.SplashActivity
import com.pingpongpacket.truckview.presentation.views.AuthFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(AuthModule::class))
interface AuthComponent {
    fun inject(authFragment: AuthFragment)
    fun inject(splashActivity: SplashActivity)
}
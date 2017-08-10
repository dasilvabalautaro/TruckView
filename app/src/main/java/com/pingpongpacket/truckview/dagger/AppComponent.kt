package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.presentation.views.AuthFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(authFragment: AuthFragment)
}
package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: App)
    fun plus(mainModule: MainModule): MainComponent
    fun plus(authModule: AuthModule): AuthComponent
    fun plus(withoutBuilderModule: WithoutBuilderModule): WithoutBuilderComponent

}
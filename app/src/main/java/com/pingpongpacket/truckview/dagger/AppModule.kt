package com.pingpongpacket.truckview.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = this.app
}
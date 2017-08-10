package com.pingpongpacket.truckview.dagger

import android.content.Context
import com.pingpongpacket.truckview.tools.InputCheck
import com.pingpongpacket.truckview.tools.Preferences
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideInputCheck(): InputCheck {
        return InputCheck(context)
    }

    @Provides
    fun providePreferences(): Preferences {
        return Preferences(context)
    }
}
package com.pingpongpacket.truckview.dagger

import android.app.Activity
import android.content.Context
import com.pingpongpacket.truckview.domain.MainContract
import com.pingpongpacket.truckview.domain.WeighingContract
import com.pingpongpacket.truckview.presentation.views.MainNavigator
import com.pingpongpacket.truckview.presentation.views.MainPresenter
import com.pingpongpacket.truckview.presentation.views.WeighingNavigator
import com.pingpongpacket.truckview.presentation.views.WeighingPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule(val mainActivity: Activity) {

    @Provides
    fun provideMainNavigation():
            MainContract.Navigator{
        return MainNavigator(mainActivity)
    }

    @Provides
    fun provideWeighingNavigation(navigator: MainContract.Navigator):
            WeighingContract.Navigator{
        return WeighingNavigator(navigator)
    }

    @Provides
    fun provideWeighingPresenter(navigator: WeighingContract.Navigator):
            WeighingContract.Presenter{
        return WeighingPresenter(navigator)
    }

    @Provides
    fun provideMainPresenter(navigator: MainContract.Navigator):
            MainContract.Presenter{
        return MainPresenter(navigator)
    }

    @Provides
    fun provideContext(): Context{
        return mainActivity
    }

}
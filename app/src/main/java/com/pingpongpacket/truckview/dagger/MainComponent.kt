package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.presentation.MainActivity
import com.pingpongpacket.truckview.presentation.views.WeighingFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(weighingFragment: WeighingFragment)
}
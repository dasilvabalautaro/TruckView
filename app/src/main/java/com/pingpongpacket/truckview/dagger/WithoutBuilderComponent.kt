package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.presentation.views.WeighingPresenter
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(WithoutBuilderModule::class))
interface WithoutBuilderComponent {
    fun inject(weighingPresenter: WeighingPresenter)
}
package com.pingpongpacket.truckview.presentation.views

import com.pingpongpacket.truckview.domain.MainContract
import com.pingpongpacket.truckview.domain.WeighingContract
import com.pingpongpacket.truckview.models.Weighing
import javax.inject.Inject

class WeighingNavigator @Inject constructor(val mainNavigator:
                                            MainContract.Navigator):
        WeighingContract.Navigator {

    override fun goToWeighingDetails(weighing: Weighing) {
        mainNavigator.goToWeighingDetails(weighing)
    }

}
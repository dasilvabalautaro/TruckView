package com.pingpongpacket.truckview.domain

import com.pingpongpacket.truckview.models.Weighing
import com.pingpongpacket.truckview.tools.BaseNavigator
import com.pingpongpacket.truckview.tools.BasePresenter
import com.pingpongpacket.truckview.tools.BaseView


interface MainContract {
    interface Navigator: BaseNavigator{
        fun goToWeighingDetails(weighing: Weighing)
        fun goToWeighing()
        fun onBackPressed(): Boolean
    }

    interface View: BaseView{
        fun highlightWeighing()
    }

    interface Presenter: BasePresenter<View>{
        fun clickWeighing()
    }
}
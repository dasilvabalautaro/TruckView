package com.pingpongpacket.truckview.domain

import com.pingpongpacket.truckview.models.Weighing
import com.pingpongpacket.truckview.tools.BaseNavigator
import com.pingpongpacket.truckview.tools.BasePresenter
import com.pingpongpacket.truckview.tools.BaseView

interface WeighingContract {
    interface Navigator: BaseNavigator{
        fun goToWeighingDetails(weighing: Weighing)
    }
    interface View: BaseView{
        fun showLoading()
        fun hideLoading()
        fun showWeighingList(weighingList: ArrayList<Weighing>)
        fun showToast(message: String)
    }
    interface Presenter: BasePresenter<WeighingContract.View>{
        fun getWeighing()
        fun clickWeighing(weighing: Weighing)
        fun loadUpdateWeighing()
    }
}
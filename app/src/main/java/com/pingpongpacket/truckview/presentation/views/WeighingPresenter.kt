package com.pingpongpacket.truckview.presentation.views

import com.pingpongpacket.truckview.domain.WeighingContract
import com.pingpongpacket.truckview.models.Weighing
import javax.inject.Inject

class WeighingPresenter @Inject constructor(val navigator:
                                            WeighingContract.Navigator):
        WeighingContract.Presenter {

    private var view: WeighingContract.View? = null

    private var listWeighing: ArrayList<Weighing>? = null

    override fun attachView(view: WeighingContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getWeighing() {
        listWeighing = ArrayList()
        for (i in 0..3){
            val weigh: Weighing = Weighing("123",
                    "12092017", "la cruz", "123ser",
                    "loco veloz", "345678", "1rd", "jorge rocha",
                    1245, "gloria", "leche", "aceros")
            listWeighing!!.add(weigh)
        }
        if (view != null){
            view!!.showWeighingList(listWeighing!!)
        }
    }

    override fun clickWeighing(weighing: Weighing) {
        navigator.goToWeighingDetails(weighing)
    }

    override fun loadUpdateWeighing() {

    }
}
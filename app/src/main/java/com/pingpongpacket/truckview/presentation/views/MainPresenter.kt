package com.pingpongpacket.truckview.presentation.views

import com.pingpongpacket.truckview.domain.MainContract
import javax.inject.Inject

class MainPresenter @Inject constructor(val mainNavigator:
                            MainContract.Navigator): MainContract.Presenter {

    var view: MainContract.View? = null

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
       this.view = null
    }

    override fun clickWeighing() {
        if (view != null){
            view!!.highlightWeighing()
        }
        mainNavigator.goToWeighing()
    }
}
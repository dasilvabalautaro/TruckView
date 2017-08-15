package com.pingpongpacket.truckview.tools


interface BasePresenter<in V: BaseView> {
    fun attachView(view: V)
    fun detachView()
}
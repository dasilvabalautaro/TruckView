package com.pingpongpacket.truckview.presentation.views

import android.os.Handler
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.dagger.WithoutBuilderModule
import com.pingpongpacket.truckview.domain.RequestWeighing
import com.pingpongpacket.truckview.domain.WeighingContract
import com.pingpongpacket.truckview.models.Weighing
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeighingPresenter @Inject constructor(val navigator:
                                            WeighingContract.Navigator):
        WeighingContract.Presenter {

    @Inject
    lateinit var requestWeighing: RequestWeighing

    val app
        get() = App()

    val component by lazy { app.component.plus(WithoutBuilderModule()) }
    private var view: WeighingContract.View? = null
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var listWeighing: ArrayList<Weighing>? = null
    private val CHECK_CACHE = 0
    private val NOT_CACHE = 1

    init {
        component.inject(this)
        val list = this.requestWeighing.observableList.map { s -> s }
        disposable.add(list.observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    kotlin.run {
                        listWeighing = s
                        if (view != null){
                            view!!.showWeighingList(listWeighing!!)
                        }
                    }
                })
    }


    override fun attachView(view: WeighingContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getWeighing() {
        requestWeighing.downloadWeighing(CHECK_CACHE)

    }
    override fun clickWeighing(weighing: Weighing) {
        navigator.goToWeighingDetails(weighing)
    }

    override fun loadUpdateWeighing() {
        Handler().postDelayed({
            if (this.view != null){
                this.view!!.hideLoading()
                requestWeighing.downloadWeighing(NOT_CACHE)
            }
        }, 2000)
    }
}
package com.pingpongpacket.truckview.presentation.views

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.dagger.MainModule
import com.pingpongpacket.truckview.domain.WeighingContract
import com.pingpongpacket.truckview.models.Weighing
import javax.inject.Inject

class WeighingFragment: Fragment(), WeighingContract.View {
    val Fragment.app: App
        get() = activity.application as App

    val component by lazy { app.component.plus(MainModule(activity)) }

    @BindView(R.id.sr_weighing)
    @JvmField var srLayout: SwipeRefreshLayout? = null
    @BindView(R.id.rv_weighing)
    @JvmField var rvWeighing: RecyclerView? = null

    @Inject
    lateinit var presenter: WeighingContract.Presenter

    var adapter: WeighingAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.weighing_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
    }


    override fun showLoading() {
        srLayout!!.isRefreshing = true
    }

    override fun hideLoading() {
        srLayout!!.isRefreshing = false
    }

    override fun showWeighingList(weighingList: ArrayList<Weighing>) {
        adapter!!.setWeighingList(weighingList)
        rvWeighing!!.scrollToPosition(0)
    }

    override fun showToast(message: String) {
        context.toast(message)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        presenter.attachView(this)
        presenter.getWeighing()
    }

    private fun setupRecyclerView(){
        rvWeighing!!.setHasFixedSize(true)
        rvWeighing!!.layoutManager = LinearLayoutManager(this.context)
        adapter = WeighingAdapter(){
            presenter.clickWeighing(it)
        }
        rvWeighing!!.adapter = adapter
    }

    private fun setupSwipeRefresh() = srLayout!!.setOnRefreshListener(
            presenter::loadUpdateWeighing)

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        if (srLayout != null){
            srLayout!!.isRefreshing = false
            srLayout!!.destroyDrawingCache()
            srLayout!!.clearAnimation()
        }
    }
}
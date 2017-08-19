package com.pingpongpacket.truckview.presentation.views

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.actinarium.aligned.TextView
import com.google.gson.Gson
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.models.Weighing

class WeighingDetailFragment: Fragment() {
    @BindView(R.id.tb_details)
    @JvmField var toolbar: Toolbar? = null
    @BindView(R.id.tv_date)
    @JvmField var tvDate: TextView? = null
    @BindView(R.id.tv_operator)
    @JvmField var tvOperator: TextView? = null
    @BindView(R.id.tv_checkpoint)
    @JvmField var tvCheckpoint: TextView? = null
    @BindView(R.id.tv_driver)
    @JvmField var tvDriver: TextView? = null
    @BindView(R.id.tv_license)
    @JvmField var tvLicense: TextView? = null
    @BindView(R.id.tv_vehicle)
    @JvmField var tvVehicle: TextView? = null
    @BindView(R.id.tv_plate)
    @JvmField var tvPlate: TextView? = null
    @BindView(R.id.tv_provider)
    @JvmField var tvProvider: TextView? = null
    @BindView(R.id.tv_client)
    @JvmField var tvClient: TextView? = null
    @BindView(R.id.tv_weigh)
    @JvmField var tvWeigh: TextView? = null


    companion object Factory {
        private val inputKey: String = "key_weighing"

        fun newInstance(arg1: Weighing? = null):
                WeighingDetailFragment = WeighingDetailFragment().apply {
            this.arguments = Bundle().apply {
                this.putParcelable(inputKey, arg1)
            }
        }

    }

    private val weighing: Weighing by lazy {
        this.arguments.getParcelable<Weighing>(inputKey) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.detail_weighing,
                container,false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setWeighing(weighing)
    }
    private fun setupToolbar(){
        toolbar!!.inflateMenu(R.menu.weighing_detail)
        toolbar!!.setNavigationIcon(R.drawable.ic_back_24dp)
        toolbar!!.setTitleTextColor(R.color.color_input_hint)
        toolbar!!.title = getString(R.string.lbl_weighing_detail)
        toolbar!!.setNavigationOnClickListener(View.OnClickListener {
            activity.onBackPressed()
        })

        val menuItem: MenuItem = toolbar!!.menu.findItem(R.id.menu_weighing_details__share)

        (MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider)
                .setShareIntent(Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getJsonObject(weighing)))
    }

    private fun getJsonObject(weighing: Weighing): String{
        val gson: Gson = Gson()
        val gsonResult = gson.toJson(weighing)
        return gsonResult
    }

    private fun setWeighing(weighing: Weighing){
        tvCheckpoint!!.text = weighing.checkPointName
        tvClient!!.text = weighing.client
        tvDate!!.text = weighing.date
        tvDriver!!.text = weighing.driver
        tvLicense!!.text = weighing.license
        tvOperator!!.text = weighing.operatorName
        tvPlate!!.text = weighing.plate
        tvProvider!!.text = weighing.provider
        tvVehicle!!.text = weighing.configVehicle
        tvWeigh!!.text = weighing.netWeight.toString()
    }
}
package com.pingpongpacket.truckview.presentation.views

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.domain.MainContract
import com.pingpongpacket.truckview.models.Weighing
import com.pingpongpacket.truckview.presentation.MainActivity
import javax.inject.Inject


class MainNavigator @Inject constructor(val mainActivity: Activity):
        MainContract.Navigator {

    val TAG_DETAILS = "tag_details"
    val TAG_MASTER = "tag_master"

    private fun clearDetails(): Boolean{

        val details: Fragment? = (mainActivity as MainActivity)
                .supportFragmentManager.findFragmentByTag(TAG_DETAILS)
        if (details != null){
            mainActivity
                    .supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .remove(details)
                    .commitNow()
            return true
        }
        return false
    }

    private fun clearMaster(){
        val master: Fragment? = (mainActivity as MainActivity)
                .supportFragmentManager.findFragmentByTag(TAG_MASTER)
        if (master != null){
            mainActivity
                    .supportFragmentManager
                    .beginTransaction()
                    .remove(master)
                    .commitNow()
        }
    }
    override fun goToWeighing() {
        clearDetails()
        val master: WeighingFragment = WeighingFragment()
        (mainActivity as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_content, master, TAG_MASTER)
                .commitNow()
    }
    override fun goToWeighingDetails(weighing: Weighing) {
        val weighingDetail: WeighingDetailFragment =
                WeighingDetailFragment.newInstance(weighing)
        (mainActivity as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fl_content, weighingDetail, TAG_DETAILS)
                .addToBackStack(null)
                .commit()

    }

    override fun onBackPressed(): Boolean {
        if ((mainActivity as MainActivity).supportFragmentManager
                .backStackEntryCount > 0){
            (mainActivity as MainActivity).supportFragmentManager
                    .popBackStackImmediate()
            return true
        }else{
            return false
        }

    }

}
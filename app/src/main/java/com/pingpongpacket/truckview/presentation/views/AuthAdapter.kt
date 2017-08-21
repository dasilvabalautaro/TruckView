package com.pingpongpacket.truckview.presentation.views

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager


class AuthAdapter(manager: FragmentManager,
                  pager: ViewPager):
        FragmentStatePagerAdapter(manager), AuthFragment.Callback {

    var pager: ViewPager? = null
    var signUp: AuthFragment? = null
    var logIn: AuthFragment? = null

    init {
        this.pager = pager

    }

    override fun remove(fragment: AuthFragment) {
        if (logIn == fragment){
            pager!!.setCurrentItem(0, true)
            signUp!!.fireAnimation()
        }else{
            pager!!.setCurrentItem(1, true)
            logIn!!.fireAnimation()
        }
    }

    override fun getItem(position: Int): AuthFragment {
        when(position){
            0 ->{

                if (signUp == null) signUp = SignUpFragment()
                signUp!!.callback = this
                return signUp!!

//                if (logIn == null) logIn = LogInFragment()
//                logIn!!.callback = this
//                return logIn!!

            }
            else ->{
                if (logIn == null) logIn = LogInFragment()
                logIn!!.callback = this

//                if (signUp == null) signUp = SignUpFragment()
//                signUp!!.callback = this

            }
        }
        return logIn!!
    }

    override fun getCount(): Int {
        return 2
    }

}
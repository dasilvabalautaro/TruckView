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
            pager!!.setCurrentItem(1, true)
            signUp!!.fireAnimation()
        }else{
            pager!!.setCurrentItem(0, true)
            logIn!!.fireAnimation()
        }
    }

    override fun getItem(position: Int): AuthFragment {
        when(position){
            0 ->{

                if (logIn == null) logIn = LogInFragment()
                logIn!!.callback = this
                return logIn!!

            }
            else ->{

                if (signUp == null) signUp = SignUpFragment()
                signUp!!.callback = this

            }
        }
        return signUp!!
    }

    override fun getCount(): Int {
        return 2
    }

}
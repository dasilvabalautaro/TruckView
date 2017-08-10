package com.pingpongpacket.truckview.presentation.views

import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator


class BounceOvershootInterpolator(tension: Float): Interpolator {
    var overshootInterpolator: OvershootInterpolator? = null
    var bounceInterpolator: BounceInterpolator? = null

    init {
        overshootInterpolator = OvershootInterpolator(tension)
        bounceInterpolator = BounceInterpolator()
    }

    override fun getInterpolation(p0: Float): Float {
        if (p0 > .99f) return bounceInterpolator!!.getInterpolation(p0)
        return overshootInterpolator!!.getInterpolation(p0)
    }

}
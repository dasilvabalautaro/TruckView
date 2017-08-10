package com.pingpongpacket.truckview.presentation

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.presentation.views.AuthAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pager: ViewPager = ButterKnife.findById(this, R.id.pager)
        pager.adapter = AuthAdapter(supportFragmentManager, pager)
    }
}

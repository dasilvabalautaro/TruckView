package com.pingpongpacket.truckview.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.dagger.MainModule
import com.pingpongpacket.truckview.domain.MainContract
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    val Activity.app: App
        get() = application as App

    val component by lazy { app.component.plus(MainModule(this)) }

    @Inject
    lateinit var mainNavigator: MainContract.Navigator
    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        mainPresenter.attachView(this)
        if (savedInstanceState == null){
            mainPresenter.clickWeighing()
        }

//        findViewById(R.id.btnInit).setOnClickListener(onClickListener)

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

//    internal val onClickListener = View.OnClickListener {
//        navigate<LoginActivity>()
//    }


    inline fun <reified T : Activity> Activity.navigate() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    override fun highlightWeighing() {

    }

    override fun onBackPressed() {
        if (!mainNavigator.onBackPressed()){
            super.onBackPressed()
        }

    }
}

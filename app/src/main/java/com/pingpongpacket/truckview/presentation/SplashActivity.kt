package com.pingpongpacket.truckview.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.dagger.AuthModule
import com.pingpongpacket.truckview.models.UserRegisterFirebase
import com.pingpongpacket.truckview.tools.ConnectionNetwork
import javax.inject.Inject

class SplashActivity: AppCompatActivity() {

    val Activity.app: App
        get() = application as App

    val component by lazy { app.component.plus(AuthModule(this)) }

    @Inject
    lateinit var userRegisterFirebaseActivity: UserRegisterFirebase

    @Inject
    lateinit var connectionNetwork: ConnectionNetwork

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        component.inject(this)

        Handler().postDelayed({
            if (connectionNetwork.isOnline()){
                if (userRegisterFirebaseActivity.isSignedIn()){
                    navigate<MainActivity>()
                }else{
                    navigate<LoginActivity>()
                }
                finish()
            }else{
                showAlertNotConnection()
            }

        }, 3000)

    }
    inline fun <reified T : Activity> Activity.navigate() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        userRegisterFirebaseActivity.finish()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun showAlertNotConnection(){
        val simpleAlert = AlertDialog.Builder(this@SplashActivity).create()
        simpleAlert.setTitle(getString(R.string.lbl_not_found_network))
        simpleAlert.setMessage(getString(R.string.lbl_mesage_not_network))

        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            dialogInterface, i ->
            finish()
        })

        simpleAlert.show()
    }
}
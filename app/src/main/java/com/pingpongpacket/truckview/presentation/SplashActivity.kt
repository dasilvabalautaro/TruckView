package com.pingpongpacket.truckview.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.dagger.AuthModule
import com.pingpongpacket.truckview.models.UserRegisterFirebase
import javax.inject.Inject

class SplashActivity: AppCompatActivity() {

    val Activity.app: App
        get() = application as App

    val component by lazy { app.component.plus(AuthModule(this)) }

    @Inject
    lateinit var userRegisterFirebaseActivity: UserRegisterFirebase

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        component.inject(this)

        Handler().postDelayed({
            if (userRegisterFirebaseActivity.isSignedIn()){
                navigate<MainActivity>()
            }else{
                navigate<LoginActivity>()
            }

            finish()
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
}
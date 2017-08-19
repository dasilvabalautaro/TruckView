package com.pingpongpacket.truckview.tools

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import com.pingpongpacket.truckview.R


class ConnectionNetwork(val context: Context) {
    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}
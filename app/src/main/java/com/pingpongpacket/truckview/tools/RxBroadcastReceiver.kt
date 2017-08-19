package com.pingpongpacket.truckview.tools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.Observable
import io.reactivex.functions.Cancellable

class RxBroadcastReceiver {
    fun create(context: Context,
               intentFilter: IntentFilter): Observable<Intent> {
        return Observable.create { subscriber ->
            val receiver = broadcastReceiver({ context, intent ->
                subscriber.onNext(intent!!) })
            context.registerReceiver(receiver, intentFilter)

            subscriber.setCancellable { Cancellable{
                receiver.abortBroadcast()
            } }

        }
    }

    private fun broadcastReceiver(init: (Context, Intent?) -> Unit):
            BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                init(context, intent)
            }
        }
    }

}
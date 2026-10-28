package com.example.mod_a2treino.ui.device

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class BateryObserver(private val context: Context) {

    val isBatteryLow: Flow<Boolean> = callbackFlow {
        val broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let { trySend(it.isLow()) }
            }
        }

       val sticky = context.registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        sticky?.let { trySend(it.isLow()) }

        awaitClose { context.unregisterReceiver(broadcastReceiver) }
    }.distinctUntilChanged()

    private fun Intent.isLow(): Boolean {
        val level = getIntExtra(BatteryManager.EXTRA_LEVEL,-1)
        val scale = getIntExtra(BatteryManager.EXTRA_SCALE,-1)

        if (scale < 0 || level <0) return false

        return (level*100)/scale <= 20

    }
}


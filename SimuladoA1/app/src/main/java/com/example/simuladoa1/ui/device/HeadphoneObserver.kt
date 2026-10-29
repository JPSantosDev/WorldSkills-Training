package com.example.simuladoa1.ui.device

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeadphoneObserver(private val context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val fonesConectados: Flow<Unit> = callbackFlow {
        val conhecidos = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            .filter { it.isFoneDeOuvido() }
            .map { it.id }
            .toMutableSet()

        val callback = object : AudioDeviceCallback(){
            override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>) {
                val novos = addedDevices.filter {
                    it.isSink && it.isFoneDeOuvido() && it.id !in conhecidos
                }
                addedDevices.forEach {
                    Log.d("Fone", "added: type=${it.type} sink=${it.isSink} id=${it.id}")
                }

                if(novos.isNotEmpty()){
                    conhecidos += novos.map { it.id }
                    trySend(Unit)
                }
            }

            override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>) {
                conhecidos -= removedDevices.map { it.id }.toSet()
            }
        }
        audioManager.registerAudioDeviceCallback(callback, Handler(Looper.getMainLooper()))
        awaitClose { audioManager.unregisterAudioDeviceCallback(callback) }
    }

    fun AudioDeviceInfo.isFoneDeOuvido() =
                type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                type == AudioDeviceInfo.TYPE_USB_HEADSET ||
                type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP ||
                type == AudioDeviceInfo.TYPE_BLE_HEADSET

}
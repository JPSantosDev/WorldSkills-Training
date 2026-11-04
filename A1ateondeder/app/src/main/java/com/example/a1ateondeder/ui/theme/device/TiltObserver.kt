package com.example.a1ateondeder.ui.theme.device

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class TiltObserver(private val context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val inclinacaoLaterao: Flow<Float> = callbackFlow {
        val sensor = accelerometer

        if(sensor == null){
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent) {
                trySend(event.values[0])
            }
        }
        sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_UI
        )

        awaitClose { sensorManager.unregisterListener(listener) }
    }.distinctUntilChanged()
}
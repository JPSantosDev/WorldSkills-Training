package com.example.tudo.ui.device

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlin.math.abs

class TiltObserver(private val context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE)as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val tiltedValue: Flow<Float> = callbackFlow {

        val sensor = accelerometer

        if (sensor == null) {
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                event?.let { trySend(it.values[0]) }
            }
        }

        sensorManager.registerListener(listener,sensor, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener) }

    }.distinctUntilChanged()

    val selecionados: Flow<Int> = flow {

        var retornou = true

        tiltedValue.collect { x ->
            when {
                retornou && x > 4f -> {
                    emit(0)
                    retornou = false
                }
                retornou && x < -4f -> {
                    emit(1)
                    retornou = false
                }
                abs(x) < 1f -> retornou = true
            }
        }
    }
}
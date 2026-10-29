package com.example.simuladoa1.ui.device

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
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val inclinacaoLateral: Flow<Float> = callbackFlow {
        val sensor = accelerometer

        if (sensor == null) {
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

            override fun onSensorChanged(event: SensorEvent) {
                trySend(event.values[0])
            }
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener, sensor) }
    }.distinctUntilChanged()

}

fun Flow<Float>.selecoesPorInclinacao(limite: Float = 3f): Flow<Int> = flow {
    var armado = true

    collect { x->
        when{

            armado && x > limite ->{
                emit(0)
                armado = false
            }

            armado && x < -limite ->{
                emit(1)
                armado = false
            }

            abs(x) < 1f -> armado = true

        }
    }
}
package com.egci428.practice

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.sqrt


class ShakeDetector : SensorEventListener {
    private var shakeTimestamp: Long = 0
    private var shakeCount: Int = 0
    private var shakeListener: OnShakeListener? = null

    interface OnShakeListener {
        fun onShake()
    }

    fun setOnShakeListener(listener: OnShakeListener) {
        this.shakeListener = listener
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("ShakeDetector", "Raw sensor data: ${event.values.contentToString()}")
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

        Log.d("ShakeDetector", "gForce: $gForce")

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }

            if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                shakeCount = 0
            }

            shakeTimestamp = now
            shakeCount++

            Log.d("ShakeDetector", "Device shaken. Count: $shakeCount")
            shakeListener?.onShake()
        }
    }


    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 0.6f
        private const val SHAKE_SLOP_TIME_MS = 500
        private const val SHAKE_COUNT_RESET_TIME_MS = 3000
    }

}



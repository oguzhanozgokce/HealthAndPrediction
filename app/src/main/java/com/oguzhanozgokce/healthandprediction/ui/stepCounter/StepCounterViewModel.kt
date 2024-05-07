package com.oguzhanozgokce.healthandprediction.ui.stepCounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepCounterViewModel(context: Context, private val goalSteps: Int) : ViewModel(), SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


    private val _stepCount = MutableLiveData<Int>()
    val stepCount: LiveData<Int>
        get() = _stepCount

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress


    init {
        _stepCount.value = 0
        _progress.value = 0
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Log.e("StepCounterViewModel", "No step counter sensor found!")
        }else {
            stepSensor?.let { sensor ->
                if (sensorManager.registerListener(
                        this,
                        sensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                ) {
                    _stepCount.value = 0

                } else {
                    sensorManager.unregisterListener(this)
                    Log.e("StepCounterViewModel", "Could not register sensor listener")
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No implementation needed
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                _stepCount.value = it.values[0].toInt()
                _progress.value = ((it.values[0] / goalSteps) * 360).toInt()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }
}
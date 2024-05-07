package com.oguzhanozgokce.healthandprediction.ui.stepCounter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StepCounterViewModelFactory(private val context: Context, private val goalSteps: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepCounterViewModel::class.java)) {
            return StepCounterViewModel(context, goalSteps) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


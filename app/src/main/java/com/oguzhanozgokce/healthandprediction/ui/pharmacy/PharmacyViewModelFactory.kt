package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oguzhanozgokce.healthandprediction.repository.PharmacyRepo

class PharmacyViewModelFactory(private val repository: PharmacyRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PharmacyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

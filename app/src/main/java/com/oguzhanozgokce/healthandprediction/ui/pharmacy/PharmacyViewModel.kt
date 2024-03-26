package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse
import com.oguzhanozgokce.healthandprediction.repository.PharmacyRepo
import kotlinx.coroutines.launch

class PharmacyViewModel(private val repository: PharmacyRepo) : ViewModel() {

    private val _pharmacies = MutableLiveData<PharmacyResponse>()
    val pharmacies: LiveData<PharmacyResponse>
        get() = _pharmacies

    fun getNearbyPharmacies(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getNearbyPharmacies(latitude, longitude)
                _pharmacies.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
package com.oguzhanozgokce.healthandprediction.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.PharmacyResponse
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {

    private val pharmacyAPI = PharmacyAPI.pharmacyService

    fun getNearbyPharmacies(latitude: Double, longitude: Double, apiKey: String): PharmacyResponse? {
        var response: PharmacyResponse? = null
        viewModelScope.launch {
            response = pharmacyAPI.getNearbyPharmacies(location = "$latitude,$longitude", apiKey = apiKey)
        }
        return response
    }
}
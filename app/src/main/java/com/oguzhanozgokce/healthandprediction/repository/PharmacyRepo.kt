package com.oguzhanozgokce.healthandprediction.repository

import com.oguzhanozgokce.healthandprediction.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse

class PharmacyRepo {

    private val pharmacyApiService = PharmacyAPI.pharmacyService
    suspend fun getNearbyPharmacies(latitude: Double, longitude: Double): PharmacyResponse {
        return pharmacyApiService.getNearbyPharmacies(latitude, longitude, PharmacyAPI.API_KEY)
    }
}
package com.oguzhanozgokce.healthandprediction.data.repos

import com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.PharmacyResponse

class PharmacyRepo {
    private val pharmacyApiService = PharmacyAPI.pharmacyService
    suspend fun getNearbyPharmacies(address: String, apiKey: String): PharmacyResponse {
        return pharmacyApiService.getNearbyPharmacies(address, apiKey)
    }
}
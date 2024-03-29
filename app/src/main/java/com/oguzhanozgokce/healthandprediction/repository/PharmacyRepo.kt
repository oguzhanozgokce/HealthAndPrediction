package com.oguzhanozgokce.healthandprediction.repository

import com.oguzhanozgokce.healthandprediction.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse

class PharmacyRepo {

    private val pharmacyApiService = PharmacyAPI.pharmacyService

    suspend fun getNearbyPharmacies(address: String, apiKey: String): PharmacyResponse {
        return pharmacyApiService.getNearbyPharmacies(address, apiKey)
    }
}
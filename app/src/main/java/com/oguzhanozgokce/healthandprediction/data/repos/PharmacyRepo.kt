package com.oguzhanozgokce.healthandprediction.data.repos

import com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI.PharmacyApiService
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.PharmacyResponse

class PharmacyRepo(private val apiService: PharmacyApiService) {
    suspend fun getPharmacies(): PharmacyResponse {
        return apiService.getPharmacies()
    }
}
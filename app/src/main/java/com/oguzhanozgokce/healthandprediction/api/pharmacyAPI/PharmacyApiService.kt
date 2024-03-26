package com.oguzhanozgokce.healthandprediction.api.pharmacyAPI

import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacyApiService {
    @GET("pharmacies-on-duty/locations")
    suspend fun getNearbyPharmacies(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("apiKey") apiKey: String
    ): PharmacyResponse
}
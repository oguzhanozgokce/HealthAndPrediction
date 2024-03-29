package com.oguzhanozgokce.healthandprediction.api.pharmacyAPI

import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PharmacyApiService {
    @GET("dutyPharmacy")
    suspend fun getNearbyPharmacies(
        @Query("location") location: String,
        @Header("authorization") apiKey: String
    ): PharmacyResponse
}
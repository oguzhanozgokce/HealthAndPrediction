package com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI

import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.PharmacyResponse
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
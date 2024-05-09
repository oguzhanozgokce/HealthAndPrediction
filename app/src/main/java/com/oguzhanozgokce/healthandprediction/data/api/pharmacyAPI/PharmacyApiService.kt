package com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI

import com.oguzhanozgokce.healthandprediction.common.Constants.API_KEY_HEALTH
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.PharmacyResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface PharmacyApiService {
    @GET("dutyPharmacy?il=Istanbul")
        suspend fun getPharmacies(
            @Header("Content-Type") contentType: String = "application/json",
           @Header("Authorization") apiKey: String = "apikey $API_KEY_HEALTH"
        ): PharmacyResponse


    /**
     * interface PharmacyApiService {
     *     @GET("dutyPharmacy")
     *     suspend fun getPharmacies(
     *         @Header("Content-Type") contentType: String = "application/json",
     *         @Header("Authorization") apiKey: String = "apikey $API_KEY_HEALTH",
     *         @Query("il") city: String
     *     ): PharmacyResponse
     * }
     */
}
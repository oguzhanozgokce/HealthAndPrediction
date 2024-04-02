package com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI

import com.oguzhanozgokce.healthandprediction.common.Constants.BASE_URL_HEALTH
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PharmacyAPI {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_HEALTH)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pharmacyService: PharmacyApiService = retrofit.create(PharmacyApiService::class.java)
}
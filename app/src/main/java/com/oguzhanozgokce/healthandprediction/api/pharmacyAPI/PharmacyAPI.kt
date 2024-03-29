package com.oguzhanozgokce.healthandprediction.api.pharmacyAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PharmacyAPI {

    private const val BASE_URL = "https://api.collectapi.com/health/"
    const val API_KEY = "3vc1hah5KrdUO2JLVZ3xkD:2m2ptbeXwPFOK9pFDeDTO9"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pharmacyService: PharmacyApiService = retrofit.create(PharmacyApiService::class.java)
}
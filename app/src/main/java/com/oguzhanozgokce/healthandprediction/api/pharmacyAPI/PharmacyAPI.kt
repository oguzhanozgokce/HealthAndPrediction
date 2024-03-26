package com.oguzhanozgokce.healthandprediction.api.pharmacyAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PharmacyAPI {

    private const val BASE_URL = "https://www.nosyapi.com/apiv2/service/"
    const val API_KEY = "Z41vuWZVTH6XtZ00nqNXXeQDXB0P74U8uEUO8gM7XcKXkjKIvxXKhfUCLrEI"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pharmacyService: PharmacyApiService = retrofit.create(PharmacyApiService::class.java)
}
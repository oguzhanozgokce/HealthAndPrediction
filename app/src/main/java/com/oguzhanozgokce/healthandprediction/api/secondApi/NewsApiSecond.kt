package com.oguzhanozgokce.healthandprediction.api.secondApi

import com.oguzhanozgokce.healthandprediction.api.newsAPI.NewsAPIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiSecond {
    private const val BASE_URL = "https://api.collectapi.com"
    const val API_KEY = "3vc1hah5KrdUO2JLVZ3xkD:2m2ptbeXwPFOK9pFDeDTO9"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val service: NewsAPIService = retrofit.create(NewsAPIService::class.java)
}
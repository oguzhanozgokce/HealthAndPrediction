package com.oguzhanozgokce.healthandprediction.data.api.newsAPI

import com.oguzhanozgokce.healthandprediction.common.Constants.BASE_URL_NEWS
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_NEWS)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val service: NewsAPIService = retrofit.create(NewsAPIService::class.java)
}
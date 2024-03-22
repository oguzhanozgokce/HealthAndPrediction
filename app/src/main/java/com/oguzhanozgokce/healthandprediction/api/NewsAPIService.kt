package com.oguzhanozgokce.healthandprediction.api

import com.oguzhanozgokce.healthandprediction.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("v2/top-headlines")  // fonksiyonu her zaman "health" kategorisinde haberleri getirir.
    suspend fun getHealthNews(
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/everything")   //"health" kategorisinde kullanıcı tarafından girilen kelimeyle arama yapar.
    suspend fun searchHealthNews(
        @Query("q") query: String,
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
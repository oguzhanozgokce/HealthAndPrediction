package com.oguzhanozgokce.healthandprediction.api.secondApi

import com.oguzhanozgokce.healthandprediction.model.modelNews.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServisSecond {
    @GET("news/getNews")
    suspend fun getHealthNews(
        @Query("country") country: String = "tr",
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int
    ): NewsResponse
}
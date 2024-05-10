package com.oguzhanozgokce.healthandprediction.data.api.newsAPI

import com.oguzhanozgokce.healthandprediction.data.model.modelNews.NewsTest
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsAPIService {
    @GET("getNews")
    suspend fun getNews(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") apiKey: String = "apikey 3vc1hah5KrdUO2JLVZ3xkD:2m2ptbeXwPFOK9pFDeDTO9",
        @Query("country") country: String = "tr",
        @Query("tag") tag: String = "health"
    ): NewsTest
}


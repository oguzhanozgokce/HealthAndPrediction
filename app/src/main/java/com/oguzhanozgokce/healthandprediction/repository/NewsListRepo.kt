package com.oguzhanozgokce.healthandprediction.repository

import com.oguzhanozgokce.healthandprediction.api.newsAPI.NewsAPIService
import com.oguzhanozgokce.healthandprediction.model.modelNews.NewsResponse

class NewsListRepo(private val apiService: NewsAPIService) {
    private val apiKey = "a22d85618b504a5bb1584c0cfaa3ba75"
    //private val category = "health"
    private val country = "tr"

    suspend fun getHealthNews(page:Int): NewsResponse {
        return apiService.getHealthNews(country,apiKey,page)
    }
}

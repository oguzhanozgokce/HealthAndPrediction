package com.oguzhanozgokce.healthandprediction.repository

import com.oguzhanozgokce.healthandprediction.api.NewsAPIService
import com.oguzhanozgokce.healthandprediction.model.NewsResponse

class NewsListRepo(private val apiService: NewsAPIService) {
    private val apiKey = "a22d85618b504a5bb1584c0cfaa3ba75"
    private val category = "health"

    suspend fun getHealthNews(page:Int): NewsResponse {
        return apiService.getHealthNews(category,apiKey,page)
    }
}

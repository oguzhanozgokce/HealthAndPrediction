package com.oguzhanozgokce.healthandprediction.data.repos

import com.oguzhanozgokce.healthandprediction.data.api.newsAPI.NewsAPIService
import com.oguzhanozgokce.healthandprediction.data.model.modelNews.NewsTest

class NewsListRepo(private val apiService: NewsAPIService) {
    suspend fun getNews(): NewsTest {
        return apiService.getNews()
    }
}

package com.oguzhanozgokce.healthandprediction.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguzhanozgokce.healthandprediction.data.model.modelNews.NewsResponse
import com.oguzhanozgokce.healthandprediction.data.repos.NewsListRepo

class NewsViewModel(private val newsListRepo: NewsListRepo) {
    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse
    private var currentPage = 1
    suspend fun getHealthNews() {
        try {
            val response = newsListRepo.getHealthNews(currentPage)
            _newsResponse.value = response
            currentPage++
        } catch (e: Exception) {
            Log.e("HomeNewsListViewModel", "Error getting health news: $e")
        }
    }
}
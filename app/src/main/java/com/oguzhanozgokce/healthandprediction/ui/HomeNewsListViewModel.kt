package com.oguzhanozgokce.healthandprediction.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguzhanozgokce.healthandprediction.model.NewsResponse
import com.oguzhanozgokce.healthandprediction.repository.NewsListRepo

class HomeNewsListViewModel(private val newsListRepo: NewsListRepo) {
    private val _newsResponse = MutableLiveData<NewsResponse>()

    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    private var currentPage = 1
    private var _isLoading = MutableLiveData<Boolean>().apply { value = false } // Başlangıç değeri atanması
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    suspend fun getHealthNews() {
        try {
            val response = newsListRepo.getHealthNews(currentPage)
            _newsResponse.value = response
            currentPage++
        } catch (e: Exception) {
            Log.e("HomeNewsListViewModel", "Error getting health news: $e")
        }
    }

    suspend fun loadMoreHealthNews() {
        if (_isLoading.value == true) return
        _isLoading.value = true
        try {
            val response = newsListRepo.getHealthNews(currentPage)
            val currentArticles = _newsResponse.value?.articles ?: emptyList()
            val newArticles = response.articles
            val combinedArticles = currentArticles + newArticles
            _newsResponse.value = NewsResponse("ok", combinedArticles.size, combinedArticles)
            // Sayfa numarasını artırma
            currentPage++
        } catch (e: Exception) {
            // Hata durumunda işlemleri burada yönetebilirsiniz
            // Log.e("HomeNewsListViewModel", "Error loading more health news: $e")
        } finally {
            _isLoading.value = false
        }
    }
}
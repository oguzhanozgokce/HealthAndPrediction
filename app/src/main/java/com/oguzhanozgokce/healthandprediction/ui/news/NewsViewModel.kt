package com.oguzhanozgokce.healthandprediction.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguzhanozgokce.healthandprediction.data.model.modelNews.NewsTest
import com.oguzhanozgokce.healthandprediction.data.repos.NewsListRepo

class NewsViewModel(private val newsListRepo: NewsListRepo) {

    private val _newsTest = MutableLiveData<NewsTest>()
    val newsTest: LiveData<NewsTest>
        get() = _newsTest


    suspend fun getNews() {
        try {
            val response = newsListRepo.getNews()
            _newsTest.value = response
        } catch (e: Exception) {
            Log.e("HomeNewsListViewModel", "Error getting news: $e")
        }
    }
}
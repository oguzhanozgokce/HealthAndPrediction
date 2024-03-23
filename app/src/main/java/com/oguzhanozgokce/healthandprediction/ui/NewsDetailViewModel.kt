package com.oguzhanozgokce.healthandprediction.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhanozgokce.healthandprediction.model.Article

class NewsDetailViewModel : ViewModel() {
    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    // Haber nesnesini alacak fonksiyon
    fun setSelectedArticle(article: Article) {
        _selectedArticle.value = article
    }
}

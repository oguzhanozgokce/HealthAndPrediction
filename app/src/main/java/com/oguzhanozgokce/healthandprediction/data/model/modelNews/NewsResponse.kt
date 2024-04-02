package com.oguzhanozgokce.healthandprediction.data.model.modelNews

import java.io.Serializable

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>) : Serializable

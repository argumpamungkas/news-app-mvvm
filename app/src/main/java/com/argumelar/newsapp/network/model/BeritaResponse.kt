package com.argumelar.newsapp.network.model

import java.io.Serializable

data class BeritaResponse(
    val last_generated: String?,
    val news: List<DataNews>?
) : Serializable

data class DataNews(
    val source: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val date: String?,
    val description: String?,
    val image: String?
) : Serializable
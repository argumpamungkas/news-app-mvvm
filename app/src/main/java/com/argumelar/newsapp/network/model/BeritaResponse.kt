package com.argumelar.newsapp.network.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls
import java.io.Serializable

data class BeritaResponse(
    val last_generated: String?,
    val news: List<DataNews>?
) : Serializable

@Entity (tableName = "table_news")
data class DataNews(
    val source: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val description: String?,
    val image: String?
) : Serializable
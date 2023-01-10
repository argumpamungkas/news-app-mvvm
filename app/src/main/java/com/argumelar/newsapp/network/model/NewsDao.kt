package com.argumelar.newsapp.network.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM table_news")
    fun findAll(): LiveData<List<DataNews>>

    @Query("SELECT COUNT(*) FROM table_news WHERE date=:date")
    suspend fun findNews(date: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(dataNews: DataNews)

    @Delete
    suspend fun deleteNews(dataNews: DataNews)
}
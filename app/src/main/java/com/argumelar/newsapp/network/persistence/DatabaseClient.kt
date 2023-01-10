package com.argumelar.newsapp.network.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.network.model.NewsDao

@Database(
    entities = [DataNews::class],
    version = 1,
    exportSchema = false
)

abstract class DatabaseClient: RoomDatabase() {

    abstract val newsDao: NewsDao

}
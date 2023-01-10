package com.argumelar.newsapp.network.persistence

import android.app.Application
import androidx.room.Room
import com.argumelar.newsapp.network.model.NewsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val moduleDatabase = module {
    single { provideDatabase(androidApplication()) }
    single { provideNews(get()) }
}

fun provideDatabase(application: Application): DatabaseClient {
    return Room.databaseBuilder(
        application,
        DatabaseClient::class.java,
        "newsDB.db"
    ).fallbackToDestructiveMigration()
        .build()
}

fun provideNews(databaseClient: DatabaseClient): NewsDao {
    return databaseClient.newsDao
}
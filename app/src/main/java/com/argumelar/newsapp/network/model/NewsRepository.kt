package com.argumelar.newsapp.network.model

import com.argumelar.newsapp.network.source.ApiEndpoint
import org.koin.dsl.module

var moduleRepository = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: ApiEndpoint,
    val db: NewsDao
) {

    suspend fun fetchLogin(
        loginUser: LoginUser
    ): LoginResponse {
        return api.signIn(loginUser)
    }

    suspend fun fetchNews(token: String, q: String): BeritaResponse {
        return api.getNews(token, q)
    }

    suspend fun fetchCategory(token: String): List<CategoryResponse> {
        return api.getCategory(token)
    }

    suspend fun find(dataNews: DataNews): Int {
        return db.findNews(dataNews.date.toString())
    }

    suspend fun add(dataNews: DataNews) {
        db.addNews(dataNews)
    }

    suspend fun delete(dataNews: DataNews){
        db.deleteNews(dataNews)
    }

}
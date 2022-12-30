package com.argumelar.newsapp.network.model

import com.argumelar.newsapp.network.source.ApiEndpoint
import org.koin.dsl.module

var moduleRepository = module {
    factory { NewsRepository(get()) }
}

class NewsRepository(
    private val api: ApiEndpoint
) {

    suspend fun fetchLogin(
        loginUser: LoginUser
    ) : LoginResponse {
        return api.signIn(loginUser)
    }

    suspend fun fetchNews(token: String) : BeritaResponse{
        return api.getNews(token)
    }

}
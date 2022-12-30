package com.argumelar.newsapp.network.source

import com.argumelar.newsapp.network.model.BeritaResponse
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.network.model.LoginResponse
import com.argumelar.newsapp.network.model.LoginUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiEndpoint {

    @POST("sign-in")
    suspend fun signIn(
        @Body loginUser: LoginUser
    ) : LoginResponse

    @GET("news")
    suspend fun getNews(
        @Header("Authorization") token : String
    ) : BeritaResponse

}
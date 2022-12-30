package com.argumelar.newsapp.network.source

import com.argumelar.newsapp.network.model.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var moduleNetwork = module {
    single { provideOkhttp() }
    single { provideRetrofit(get()) }
    single { provideNewsApi(get()) }
}

private fun provideOkhttp(): OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideNewsApi( retrofit: Retrofit): ApiEndpoint = retrofit.create(ApiEndpoint::class.java)
package com.argumelar.newsapp.ui.news.home

import android.content.Context
import android.net.http.HttpResponseCache
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.network.model.*
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.dsl.module
import retrofit2.HttpException

var moduleHomeViewModel = module {
    factory { HomeViewModel(get(), get()) }
}

class HomeViewModel(private val repository: NewsRepository, val context: Context) : ViewModel() {

    private val sharedPref = PreferencesLogin(context)

    val newsList by lazy { MutableLiveData<BeritaResponse>() }
    val token by lazy { MutableLiveData<Boolean>() }
    val message by lazy { MutableLiveData<String>() }

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNews("Bearer ${sharedPref.getToken(Constant.TOKEN)}")
                newsList.value = response
                token.value = true
            } catch (e: HttpException) {
                if (e.code() == 401 ){
                    clearPref()
                    token.value = false
                    message.value = "Terjadi kesalahan, silahkan login kembali"
                }
            }
        }
    }

    fun clearPref() {
        sharedPref.clear()
    }

}
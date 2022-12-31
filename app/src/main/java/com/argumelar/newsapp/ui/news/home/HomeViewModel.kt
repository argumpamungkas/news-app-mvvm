package com.argumelar.newsapp.ui.news.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.network.model.*
import kotlinx.coroutines.launch
import org.koin.dsl.module

var moduleHomeViewModel = module {
    factory { HomeViewModel(get(), get()) }
}

class HomeViewModel(private val repository: NewsRepository, val context: Context) : ViewModel() {

    private val sharedPref = PreferencesLogin(context)

    val newsList by lazy { MutableLiveData<BeritaResponse>() }
    val token by lazy { MutableLiveData<Boolean>() }

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = repository.fetchNews("Bearer ${sharedPref.getToken(Constant.TOKEN)}")
                newsList.value = response
                token.value = true
            } catch (e: Exception) {
                sharedPref.clear()
                token.value = false
            }
        }
    }

    fun clearPref() {
        sharedPref.clear()
    }

}
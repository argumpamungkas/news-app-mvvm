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

class HomeViewModel(private val repository: NewsRepository, val context: Context): ViewModel() {

    private val sharedPref = PreferencesLogin(context)

    val newsList by lazy { MutableLiveData<BeritaResponse>() }

    init {
        fetchNews()
    }

    private fun fetchNews(){
        viewModelScope.launch {
            val response = repository.fetchNews("Bearer ${sharedPref.getToken(Constant.TOKEN)}")
            newsList.value = response
            Log.i("data", response.toString())
        }
    }

    fun clearPref(){
        sharedPref.clear()
    }

}
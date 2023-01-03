package com.argumelar.newsapp.ui.news.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.network.model.*
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
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
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val category by lazy { MutableLiveData<Int>() }

    init {
        category.value = 1
        fetchNews()
    }

    private fun fetchNews() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetchNews("Bearer ${sharedPref.getToken(Constant.TOKEN)}")
                newsList.value = response
                token.value = true
                isLoading.value = false
            } catch (e: HttpException) {
                isLoading.value = false
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

    val categories = listOf<CategoryModel>(
        CategoryModel(1, "Berita Utama"),
        CategoryModel(2, "Sains"),
        CategoryModel(3, "Desain"),
        CategoryModel(4, "Edukasi"),
        CategoryModel(5, "Olahraga"),
        CategoryModel(6, "Teknologi"),
    )

}
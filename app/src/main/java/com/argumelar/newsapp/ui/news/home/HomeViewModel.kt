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

    private val _newsList = MutableLiveData<BeritaResponse>()
    val newsList: LiveData<BeritaResponse> = _newsList

    private val _token = MutableLiveData<Boolean>()
    val token: LiveData<Boolean> = _token

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _category = MutableLiveData<Int>()
    val category: LiveData<Int> = _category

    init {
        _category.value = 1
    }

    fun fetchNews() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val tokenLogin = sharedPref.getToken(Constant.TOKEN)
                val response = repository.fetchNews("Bearer $tokenLogin", _category.value!!)
                _newsList.value = response
                _token.value = true
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                if (e.code() == 401 ){
                    clearPref()
                    _token.value = false
                    _message.value = "Terjadi kesalahan, silahkan login kembali"
                }
            }
        }
    }

    fun selectCategory(id: Int){
        _category.postValue(id)
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
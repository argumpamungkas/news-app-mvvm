package com.argumelar.newsapp.ui.news.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.network.model.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val moduleDetailViewModel = module {
    factory { DetailViewModel(get()) }
}
class DetailViewModel(private val repository: NewsRepository): ViewModel() {

    private val _isBookmark = MutableLiveData<Int>(0)
    val isBookmark: LiveData<Int> = _isBookmark

    fun find(dataNews: DataNews){
        viewModelScope.launch {
            _isBookmark.value = repository.find(dataNews)
        }
    }

    fun bookmark(dataNews: DataNews){
        viewModelScope.launch {
            if (_isBookmark.value == 0) {
                repository.add(dataNews)
            } else {
                repository.delete(dataNews)
            }
            find(dataNews)
        }
    }

}
package com.argumelar.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import com.argumelar.newsapp.network.model.NewsRepository
import org.koin.dsl.module

val moduleBookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}
class BookmarkViewModel(private val repository: NewsRepository): ViewModel() {

    val news = repository.db.findAll()

}
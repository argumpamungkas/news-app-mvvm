package com.argumelar.newsapp

import android.app.Application
import com.argumelar.newsapp.network.model.moduleRepository
import com.argumelar.newsapp.network.source.moduleNetwork
import com.argumelar.newsapp.ui.detail.moduleDetailActivity
import com.argumelar.newsapp.ui.login.moduleLoginActivity
import com.argumelar.newsapp.ui.login.moduleLoginViewModel
import com.argumelar.newsapp.ui.news.home.moduleHomeActivity
import com.argumelar.newsapp.ui.news.home.moduleHomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(
                moduleNetwork,
                moduleRepository,
                moduleLoginActivity,
                moduleLoginViewModel,
                moduleHomeActivity,
                moduleHomeViewModel,
                moduleDetailActivity
            )
        }

    }

}
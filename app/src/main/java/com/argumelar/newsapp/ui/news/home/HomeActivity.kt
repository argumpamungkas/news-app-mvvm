package com.argumelar.newsapp.ui.news.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.argumelar.newsapp.R
import com.argumelar.newsapp.adapter.AdapterListNews
import com.argumelar.newsapp.databinding.ActivityHomeBinding
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.ui.login.LoginActivity
import com.argumelar.newsapp.ui.news.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

var moduleHomeActivity = module {
    factory { HomeActivity() }
}

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.token.observe(this, Observer {
            if (it == false){
                viewModel.message.observe(this, Observer { msg ->
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                })
                moveLogin()
            }
            Log.i("token_exp", it.toString())
        })

        binding.rvListNews.adapter = adapterNews
        viewModel.newsList.observe(this, Observer {
            binding.pbLoading.visibility = if (it.news.isNullOrEmpty()) View.VISIBLE else View.GONE
            adapterNews.setData(it.news!!)
        })

    }

    private val adapterNews by lazy {
        AdapterListNews(arrayListOf(), object : AdapterListNews.OnAdapterListener {
            override fun onClick(news: DataNews) {
                startActivity(
                    Intent(this@HomeActivity, DetailActivity::class.java)
                        .putExtra("detail_news", news)
                )
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel.clearPref()
                moveLogin()
                true
            }
            else -> true
        }
    }

    private fun moveLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
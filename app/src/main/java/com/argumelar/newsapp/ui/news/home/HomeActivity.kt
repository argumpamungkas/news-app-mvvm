package com.argumelar.newsapp.ui.news.home

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.argumelar.newsapp.R
import com.argumelar.newsapp.adapter.AdapterListNews
import com.argumelar.newsapp.adapter.CategoryAdapter
import com.argumelar.newsapp.databinding.ActivityHomeBinding
import com.argumelar.newsapp.network.model.CategoryResponse
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
        supportActionBar?.hide()

        viewModel.token.observe(this, Observer {
            if (it == false) {
                viewModel.message.observe(this, Observer { msg ->
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                })
                moveLogin()
            }
            Log.i("token_exp", it.toString())
        })

        binding.rvListCategory.adapter = adapterCategory
        viewModel.category.observe(this, Observer {
            binding.shimmerCategory.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
            adapterCategory.setData(it as ArrayList<CategoryResponse>)
        })

        viewModel.chooseCategory.observe(this, Observer {
            viewModel.fetchNews()
        })

        binding.rvListNews.adapter = adapterNews
        viewModel.newsList.observe(this, Observer {
            adapterNews.setData(it.news!!)
        })

        viewModel.isLoading.observe(this, Observer {
            loading(it)
        })

        binding.fabLogout.setOnClickListener {
            viewModel.clearPref()
            moveLogin()
        }
    }

    private val adapterCategory by lazy {
        CategoryAdapter(arrayListOf(), object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryResponse) {
                viewModel.selectCategory(category.id)
                Toast.makeText(applicationContext, category.title, Toast.LENGTH_SHORT).show()
            }
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

    private fun loading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                binding.shimmerNews.visibility = View.VISIBLE
                rvListNews.visibility = View.INVISIBLE
            } else {
                binding.shimmerNews.visibility = View.GONE
                rvListNews.visibility = View.VISIBLE
            }
        }
    }

    private fun moveLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
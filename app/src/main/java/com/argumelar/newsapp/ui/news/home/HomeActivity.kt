package com.argumelar.newsapp.ui.news.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.adapter.AdapterListNews
import com.argumelar.newsapp.adapter.CategoryAdapter
import com.argumelar.newsapp.databinding.ActivityHomeBinding
import com.argumelar.newsapp.network.model.CategoryResponse
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.ui.bookmark.BookmarkActivity
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
            if (it == false) moveLogin()
        })

        viewModel.message.observe(this, Observer {
            showMessage(it)
        })

        binding.rvListCategory.adapter = adapterCategory
        viewModel.category.observe(this, Observer {
            binding.shimmerCategory.visibility =
                if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
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

        binding.ibBookmark.setOnClickListener {
            startActivity(Intent(this, BookmarkActivity::class.java))
        }

        binding.fabLogout.setOnClickListener {
            viewModel.clearPref()
            moveLogin()
        }

        onScroll()
    }

    private val adapterCategory by lazy {
        CategoryAdapter(arrayListOf(), object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryResponse?) {
                var send_id = ""
                if (category != null){
                    send_id = category.id
                }
                viewModel.selectCategory(send_id)
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

    private fun onScroll() {
        binding.apply {
            rvListNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 10) {
                        fabLogout.hide()
                    }
                    if (dy < 0) {
                        fabLogout.show()
                    }
                }
            })
        }
    }

    private fun loading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                rvListNews.visibility = View.INVISIBLE
                binding.shimmerNews.visibility = View.VISIBLE
            } else {
                rvListNews.visibility = View.VISIBLE
                binding.shimmerNews.visibility = View.GONE
            }
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    private fun moveLogin() {
        val visible = AnimationUtils.loadAnimation(this, com.google.android.material.R.anim.abc_fade_in)
        val gone = AnimationUtils.loadAnimation(this, com.google.android.material.R.anim.abc_fade_out)

        binding.loadingLogout.visibility = View.VISIBLE
        binding.loadingLogout.startAnimation(visible)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            binding.loadingLogout.startAnimation(gone)
            binding.loadingLogout.visibility = View.GONE
        }, 2000)
    }
}
package com.argumelar.newsapp.ui.bookmark

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import com.argumelar.newsapp.R
import com.argumelar.newsapp.adapter.AdapterListNews
import com.argumelar.newsapp.databinding.ActivityBookmarkBinding
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.ui.news.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

var moduleBookmarkActivity = module {
    factory { BookmarkActivity() }
}

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnBack.setOnClickListener { onSupportNavigateUp() }

        binding.rvListNews.adapter = adapterNews
        viewModel.news.observe(this){
            binding.tvWarning.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            adapterNews.setData(it)
        }


    }

    private val adapterNews by lazy {
        AdapterListNews(arrayListOf(), object : AdapterListNews.OnAdapterListener {
            override fun onClick(news: DataNews) {
                startActivity(
                    Intent(this@BookmarkActivity, DetailActivity::class.java)
                        .putExtra("detail_news", news)
                )
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
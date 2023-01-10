package com.argumelar.newsapp.ui.news.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.ActivityDetailBinding
import com.argumelar.newsapp.network.model.DataNews
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

var moduleDetailActivity = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    private val detail by lazy {
        intent.getSerializableExtra("detail_news") as DataNews
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            title = ""
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }

        detail.let {
            viewModel.find( it )
            val web = binding.webView
            web.loadUrl(detail.link!!)
            web.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.pbLoading.visibility = View.GONE
                }
            }
            val setting = binding.webView.settings
            setting.javaScriptCanOpenWindowsAutomatically = false
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark, menu)
        val menuBookmark = menu?.findItem(R.id.action_bookmark)
        menuBookmark?.setOnMenuItemClickListener {
            viewModel.bookmark(detail)
            true
        }
        viewModel.isBookmark.observe(this){
            if (it == 0){
                menuBookmark?.setIcon(R.drawable.ic_baseline_bookmark_add_24)
            } else {
                menuBookmark?.setIcon(R.drawable.ic_baseline_bookmark_added_24)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
}
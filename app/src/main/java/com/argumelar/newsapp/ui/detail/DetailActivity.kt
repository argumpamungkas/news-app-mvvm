package com.argumelar.newsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.ActivityDetailBinding
import com.argumelar.newsapp.network.model.DataNews
import org.koin.dsl.module

var moduleDetailActivity = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detail by lazy {
        intent.getSerializableExtra("detail_news") as DataNews
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar.apply {
            this!!.setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        detail.let {
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
}
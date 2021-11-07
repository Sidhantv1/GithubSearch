package com.example.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setWebView()
        setToolbar()
    }

    private fun setToolbar() {
        title = getString(R.string.repository_data_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setWebView() {
        val projectLink = intent.getSerializableExtra("link") as String
        wvRepositoryLink.settings.javaScriptEnabled = true
        wvRepositoryLink.loadUrl(projectLink)
    }
}
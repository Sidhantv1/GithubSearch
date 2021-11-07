package com.example.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubsearch.R
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Web View screen used to display the webview
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setWebView()
        setToolbar()
    }

    /**
     * Method to handle back button and set title on toolbar.
     */
    private fun setToolbar() {
        title = getString(R.string.repository_data_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Method to handle back press
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Method to setup Webview
     */
    private fun setWebView() {
        val projectLink = intent.getSerializableExtra("link") as String
        wvRepositoryLink.settings.javaScriptEnabled = true
        wvRepositoryLink.loadUrl(projectLink)
    }
}
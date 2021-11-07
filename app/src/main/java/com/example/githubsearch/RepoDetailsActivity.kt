package com.example.githubsearch

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubsearch.db.GithubRepoDBDataClass
import kotlinx.android.synthetic.main.activity_repo_details.*


class RepoDetailsActivity : AppCompatActivity() {

    lateinit var repositoryData: GithubRepoDBDataClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        setToolbar()
        setRepoData()
        openWebView()
    }

    private fun setToolbar() {
        title = getString(R.string.repository_detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setRepoData() {

        repositoryData = intent.getSerializableExtra("repoData") as GithubRepoDBDataClass
        repositoryData.apply {
            Glide.with(this@RepoDetailsActivity)
                .load(repositoryData.repositoryImage)
                .into(ivGithubImage)
            tvGithubName.text = this.repositoryFullName
            tvGithubPrjLink.text = this.projectLink
            tvGithubPrjLink.paintFlags = tvGithubPrjLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            tvGithubPrjContributor.text = this.projectContributor
            tvGithubPrjDescription.text = this.projectDescription
        }

    }

    private fun openWebView() {
        tvGithubPrjLink.setOnClickListener {
            val i = Intent(this, WebViewActivity::class.java)
            i.putExtra("link", repositoryData.projectLink)
            startActivity(i)
        }

    }
}
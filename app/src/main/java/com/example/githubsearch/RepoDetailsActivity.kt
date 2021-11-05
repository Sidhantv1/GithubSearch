package com.example.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubsearch.modelClass.Item
import kotlinx.android.synthetic.main.activity_repo_details.*


class RepoDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        val i = intent
        val repoData = i.getSerializableExtra("repoData") as Item
        tvGithubName.text = repoData.full_name
        tvGithubPrjLink.text = repoData.url
        tvGithubPrjContributor.text = repoData.contributors_url
        tvGithubPrjDescription.text = repoData.description
    }
}
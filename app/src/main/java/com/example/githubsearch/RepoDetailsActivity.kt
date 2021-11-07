package com.example.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubsearch.db.Subscriber
import kotlinx.android.synthetic.main.activity_repo_details.*


class RepoDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        val i = intent
        val repoData = i.getSerializableExtra("repoData") as Subscriber
        tvGithubName.text = repoData.repositoryFullName
        tvGithubPrjLink.text = repoData.projectLink
        tvGithubPrjContributor.text = repoData.projectContributor
        tvGithubPrjDescription.text = repoData.projectDescription
    }
}
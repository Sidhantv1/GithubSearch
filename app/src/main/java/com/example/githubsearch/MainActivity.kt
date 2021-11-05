package com.example.githubsearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.modelClass.Item
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var githubViewModel: GithubViewModel

    var arrayList = ArrayList<Item>()

    lateinit var adapter: GitHubRepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setObserver()
        setRecyclerView()
        setEditTextListener()
    }

    private fun setEditTextListener() {
        edtSearchRepository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                githubViewModel.loadGithubRepo(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setAdapter() {
        adapter.setData(arrayList)
    }

    private fun setRecyclerView() {
        rvGithubRepositories?.layoutManager = LinearLayoutManager(this)
        adapter = GitHubRepositoryAdapter { item -> doClick(item) }
        rvGithubRepositories?.adapter = adapter
    }

    private fun doClick(repoData: Item) {
        val i = Intent(this, RepoDetailsActivity::class.java)
        i.putExtra("repoData", repoData)
        startActivity(i)
    }

    private fun setObserver() {
        githubViewModel =
            ViewModelProviders.of(this).get(GithubViewModel::class.java)

        githubViewModel.githubRepoObserver().observe(
            this,
            Observer { gitHubSearch ->
                arrayList.clear()
                gitHubSearch?.items?.let { githubRepositories -> arrayList.addAll(githubRepositories) }
                setAdapter()
            })
    }
}
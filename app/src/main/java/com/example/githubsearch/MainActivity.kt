package com.example.githubsearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.modelClass.Item
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var githubViewModel: GithubViewModel

    var arrayList = ArrayList<Item>()

    lateinit var adapter: GitHubRepositoryAdapter

    var page = 1

    var limit = 20

    var enteredText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setObserver()
        setRecyclerView()
        setEditTextListener()
        setPaginationOnScrolling()
    }

    private fun setEditTextListener() {
        edtSearchRepository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enteredText = s.toString()
                getData(enteredText, page, limit)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setPaginationOnScrolling() {
        nsvGithubRepository.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                getData(enteredText, page, limit)
            }
        })
    }

    private fun setAdapter() {
        progressBar.visibility = View.GONE
        adapter.setData(arrayList)
    }

    private fun setRecyclerView() {
        rvGithubRepositories?.layoutManager = LinearLayoutManager(this)
        adapter = GitHubRepositoryAdapter { item -> doClick(item) }
        rvGithubRepositories?.adapter = adapter
    }

    private fun getData(searchedRepository: String, page: Int, limit: Int) {
        progressBar.visibility = View.VISIBLE
        githubViewModel.loadGithubRepo(searchedRepository, page, limit)
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
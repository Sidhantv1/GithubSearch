package com.example.githubsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.ModelClass.Item
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var githubRepositoriesViewModel: GithubRepositoriesViewModel

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
                githubRepositoriesViewModel.loadGithubRepositories(s.toString())
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
        adapter = GitHubRepositoryAdapter()
        rvGithubRepositories?.adapter = adapter
    }

    private fun setObserver() {
        githubRepositoriesViewModel =
            ViewModelProviders.of(this).get(GithubRepositoriesViewModel::class.java)

        githubRepositoriesViewModel.githubRepositoriesLiveData.observe(
            this,
            Observer { gitHubSearch ->
                arrayList.clear()
                gitHubSearch?.items?.let { githubRepositories -> arrayList.addAll(githubRepositories) }
                setAdapter()
            })
    }
}
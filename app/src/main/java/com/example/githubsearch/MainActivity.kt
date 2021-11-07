package com.example.githubsearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.db.Subscriber
import com.example.githubsearch.db.SubscriberDataBase
import com.example.githubsearch.db.SubscriberRepository
import com.example.githubsearch.modelClass.Item
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var githubViewModel: GithubViewModel

    private var arrayList = ArrayList<Item>()

    private lateinit var adapter: GitHubRepositoryAdapter

    var page = Constants.PAGE

    var limit = Constants.LIMIT

    var enteredText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        githubViewModel = ViewModelProviders.of(this, factory).get(GithubViewModel::class.java)
        setObserver()
        setRecyclerView()
        setEditTextListener()
        setPaginationOnScrolling()
    }

    private fun setEditTextListener() {
        edtSearchRepository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (githubViewModel.isNetworkAvailable(this@MainActivity)) {
                    arrayList.clear()
                    enteredText = s.toString()
                    getData(enteredText, page, limit)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
        if (githubViewModel.isNetworkAvailable(this))
            progressBar.visibility = View.GONE
        val list = arrayListOf<Subscriber>()
        arrayList.forEach {
            list.add(
                Subscriber(
                    id = 0,
                    repositoryImage = it.owner.avatar_url,
                    repositoryFullName = it.full_name,
                    projectLink = it.url,
                    projectContributor = it.contributors_url,
                    projectDescription = it.description
                )
            )
        }
        if (arrayList.size == 0) {
            page = 1
            tvSearchRepoTitle.visibility = View.VISIBLE
            rvGithubRepositories.visibility = View.GONE
        } else {
            tvSearchRepoTitle.visibility = View.GONE
            rvGithubRepositories.visibility = View.VISIBLE
        }
        adapter.setData(list)
    }

    private fun setRecyclerView() {
        rvGithubRepositories?.layoutManager = LinearLayoutManager(this)
        adapter = GitHubRepositoryAdapter { item -> doClick(item) }
        rvGithubRepositories?.adapter = adapter
    }

    private fun getData(searchedRepository: String, page: Int, limit: Int) {
        if (githubViewModel.isNetworkAvailable(this))
            progressBar.visibility = View.VISIBLE
        githubViewModel.loadGithubRepo(searchedRepository, page, limit)
    }

    private fun doClick(repoData: Subscriber) {
        val i = Intent(this, RepoDetailsActivity::class.java)
        i.putExtra("repoData", repoData)
        startActivity(i)
    }

    private fun setObserver() {
        githubViewModel.githubRepoObserver().observe(
            this,
            Observer { gitHubSearch ->
                if (githubViewModel.isNetworkAvailable(this)) {
                    gitHubSearch?.items?.let { githubRepositories ->
                        arrayList.addAll(githubRepositories)
                        if (arrayList.size <= Constants.DB_LIMIT) {
                            githubRepositories.forEach { repositoryDetail ->
                                githubViewModel.insertDataInDB(
                                    Subscriber(
                                        0,
                                        repositoryDetail.owner.avatar_url,
                                        repositoryDetail.full_name,
                                        repositoryDetail.url,
                                        repositoryDetail.contributors_url,
                                        repositoryDetail.description
                                    )
                                )
                            }
                        }
                    }
                    setAdapter()
                }
            })

        githubViewModel.subscribers.observe(this, Observer {
            if (!githubViewModel.isNetworkAvailable(this)) {
                if (it.isNotEmpty()) {
                    tvSearchRepoTitle.visibility = View.GONE
                    rvGithubRepositories.visibility = View.VISIBLE
                    adapter.setData(it)
                } else {
                    tvSearchRepoTitle.visibility = View.VISIBLE
                    rvGithubRepositories.visibility = View.GONE
                }
            }
        })

    }
}
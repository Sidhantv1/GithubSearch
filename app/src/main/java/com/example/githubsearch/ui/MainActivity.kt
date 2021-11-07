package com.example.githubsearch.ui

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
import com.example.githubsearch.*
import com.example.githubsearch.db.GithubRepoDBDataClass
import com.example.githubsearch.db.GithubRepoDataBase
import com.example.githubsearch.db.GithubRepository
import com.example.githubsearch.modelClass.Item
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Search Repository Screen
 */
class MainActivity : AppCompatActivity() {

    // ViewModel of the activity
    private lateinit var githubViewModel: GithubViewModel

    // ArrayList to hold the github Repositories of type Item
    private var githubRepoArrayList = ArrayList<Item>()

    // Adapter showing the searched repositories
    private lateinit var adapter: GitHubRepositoryAdapter

    // page parameter sent in the Api request
    var page = Constants.PAGE

    // Response of repositories per request
    var limit = Constants.LIMIT

    // Searched text
    var enteredText = ""

    /**
     * Method called when the activity is first created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = GithubRepoDataBase.getInstance(application).githubRepoDAO
        val repository = GithubRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        githubViewModel = ViewModelProviders.of(this, factory).get(GithubViewModel::class.java)
        setObserver()
        setRecyclerView()
        setEditTextListener()
        setPaginationOnScrolling()
    }

    /**
     * Method called when text is entered in the edittext to search the repo
     */
    private fun setEditTextListener() {
        edtSearchRepository.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (githubViewModel.isNetworkAvailable(this@MainActivity)) {
                    githubRepoArrayList.clear()
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

    /**
     * Method called to setup pagination and recall the api when reached at the bottom
     */
    private fun setPaginationOnScrolling() {
        nsvGithubRepository.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                getData(enteredText, page, limit)
            }
        })
    }

    /**
     * Method called to setup adapter
     */
    private fun setAdapter() {
        if (githubViewModel.isNetworkAvailable(this))
            progressBar.visibility = View.GONE
        val list = arrayListOf<GithubRepoDBDataClass>()
        githubRepoArrayList.forEach {
            list.add(
                GithubRepoDBDataClass(
                    id = 0,
                    repositoryImage = it.owner.avatar_url,
                    repositoryFullName = it.full_name,
                    projectLink = it.url,
                    projectContributor = it.contributors_url,
                    projectDescription = it.description
                )
            )
        }
        if (githubRepoArrayList.size == 0) {
            page = 1
            tvSearchRepoTitle.visibility = View.VISIBLE
            rvGithubRepositories.visibility = View.GONE
        } else {
            tvSearchRepoTitle.visibility = View.GONE
            rvGithubRepositories.visibility = View.VISIBLE
        }
        adapter.setData(list)
    }

    /**
     * Method to init recycler view
     */
    private fun setRecyclerView() {
        rvGithubRepositories?.layoutManager = LinearLayoutManager(this)
        adapter = GitHubRepositoryAdapter { item -> doClick(item) }
        rvGithubRepositories?.adapter = adapter
    }

    /**
     * Method to call the Api
     */
    private fun getData(searchedRepository: String, page: Int, limit: Int) {
        if (githubViewModel.isNetworkAvailable(this))
            progressBar.visibility = View.VISIBLE
        githubViewModel.loadGithubRepo(searchedRepository, page, limit)
    }

    /**
     * Method to see the selected repo detail on the click
     */
    private fun doClick(repoData: GithubRepoDBDataClass) {
        val i = Intent(this, RepoDetailsActivity::class.java)
        i.putExtra("repoData", repoData)
        startActivity(i)
    }

    /**
     * Method to set the observer
     */
    private fun setObserver() {
        // observer to setup the data from the api response when internet connection is there
        githubViewModel.githubRepoObserver().observe(
            this,
            Observer { gitHubSearch ->
                if (githubViewModel.isNetworkAvailable(this)) {
                    gitHubSearch?.items?.let { githubRepositories ->
                        githubRepoArrayList.addAll(githubRepositories)
                        // saving in the db only till certain limited value
                        if (githubRepoArrayList.size <= Constants.DB_LIMIT) {
                            githubRepositories.forEach { repositoryDetail ->
                                githubViewModel.insertDataInDB(
                                    GithubRepoDBDataClass(
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
        // observer of the db when internet connection is not there to setup the data from the db on screen
        githubViewModel.githubRepositories.observe(this, Observer {
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
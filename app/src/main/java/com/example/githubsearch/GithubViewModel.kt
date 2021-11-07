package com.example.githubsearch

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.db.GithubRepoDBDataClass
import com.example.githubsearch.db.GithubRepository
import kotlinx.coroutines.launch


class GithubViewModel(private val repository: GithubRepository) : ViewModel() {

    private val githubRepository = GithubRepository()

    val githubRepositories = repository.githubRepositories

    fun loadGithubRepo(searchedRepository: String, page: Int, limit: Int) {
        githubRepository.loadGithubRepositories(searchedRepository, page, limit)
    }

    /**
     * Method to return the response of Github Repositories API as Live data
     */
    fun githubRepoObserver() = githubRepository.githubRepositoriesLiveData


    fun insertDataInDB(githubRepoDBDataClass: GithubRepoDBDataClass) {
        insert(githubRepoDBDataClass)
    }

    private fun insert(githubRepoDBDataClass: GithubRepoDBDataClass) = viewModelScope.launch {
        Event(repository.insert(githubRepoDBDataClass))
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

}
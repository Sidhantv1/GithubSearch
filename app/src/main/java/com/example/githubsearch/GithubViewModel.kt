package com.example.githubsearch

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.db.GithubRepoDBDataClass
import com.example.githubsearch.db.GithubRepository
import kotlinx.coroutines.launch


/**
 * ViewModel class
 */
class GithubViewModel(private val repository: GithubRepository) : ViewModel() {

    // Repo Class
    private val githubRepository = GithubRepository()

    val githubRepositories = repository.githubRepositories

    /**
     * Method to call the Api
     */
    fun loadGithubRepo(searchedRepository: String, page: Int, limit: Int) {
        githubRepository.loadGithubRepositories(searchedRepository, page, limit)
    }

    /**
     * Method to return the response of Github Repositories API as Live data
     */
    fun githubRepoObserver() = githubRepository.githubRepositoriesLiveData


    /**
     * Method to check for the Github api failure
     */
    fun githubRepoApiFailure() = githubRepository.githubRepositoriesApiFailureLiveData

    /**
     * Methods to insert the data in the db
     */
    fun insertDataInDB(githubRepoDBDataClass: GithubRepoDBDataClass) {
        insert(githubRepoDBDataClass)
    }

    /**
     * Methods to insert the data in the db
     */
    private fun insert(githubRepoDBDataClass: GithubRepoDBDataClass) = viewModelScope.launch {
        repository.insert(githubRepoDBDataClass)
    }

    /**
     * Methods to check if the internet connection is available or not
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

}
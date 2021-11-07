package com.example.githubsearch

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.db.Subscriber
import com.example.githubsearch.db.SubscriberRepository
import kotlinx.coroutines.launch


class GithubViewModel(private val repository: SubscriberRepository) : ViewModel() {

    private val githubRepository = GithubRepository()

    val subscribers = repository.subscribers

    fun loadGithubRepo(searchedRepository: String, page: Int, limit: Int) {
        githubRepository.loadGithubRepositories(searchedRepository, page, limit)
    }

    /**
     * Method to return the response of Github Repositories API as Live data
     */
    fun githubRepoObserver() = githubRepository.githubRepositoriesLiveData


    fun insertDataInDB(subscriber: Subscriber) {
        insert(subscriber)
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch {
        Event(repository.insert(subscriber))
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

}
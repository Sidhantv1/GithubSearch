package com.example.githubsearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class GithubViewModel(app: Application) : AndroidViewModel(app) {

    private val githubRepository = GithubRepository()

    fun loadGithubRepo(searchedRepository: String, page: Int, limit: Int) {
        githubRepository.loadGithubRepositories(searchedRepository, page, limit)
    }

    /**
     * Method to return the response of Github Repositories API as Live data
     */
    fun githubRepoObserver() = githubRepository.githubRepositoriesLiveData

}
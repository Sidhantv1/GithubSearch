package com.example.githubsearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class GithubViewModel(app: Application) : AndroidViewModel(app) {

    private val githubRepository = GithubRepository()

    fun loadGithubRepo(searchedRepository: String) {
        githubRepository.loadGithubRepositories(searchedRepository)
    }

    /**
     * Method to return the response of Github Repositories API as Live data
     */
    fun githubRepoObserver() = githubRepository.githubRepositoriesLiveData

}
package com.example.githubsearch.db

class GithubRepository(private val dao: GithubRepoDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(githubRepoDBDataClass: GithubRepoDBDataClass): Long {
        return dao.insertSubscriber(githubRepoDBDataClass)
    }

}
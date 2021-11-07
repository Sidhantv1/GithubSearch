package com.example.githubsearch.db

/**
 * DB Class of Github Repository
 */
class GithubRepository(private val dao: GithubRepoDAO) {

    val githubRepositories = dao.getAllGithubRepositories()

    suspend fun insert(githubRepoDBDataClass: GithubRepoDBDataClass): Long {
        return dao.insertGithubRepositories(githubRepoDBDataClass)
    }

}
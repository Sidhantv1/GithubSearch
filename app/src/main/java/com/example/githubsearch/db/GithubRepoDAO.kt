package com.example.githubsearch.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Github Repo DAO
 */
@Dao
interface GithubRepoDAO {

    @Insert
    suspend fun insertGithubRepositories(githubRepoDBDataClass: GithubRepoDBDataClass): Long

    @Query("SELECT * FROM data_table")
    fun getAllGithubRepositories(): LiveData<List<GithubRepoDBDataClass>>

}
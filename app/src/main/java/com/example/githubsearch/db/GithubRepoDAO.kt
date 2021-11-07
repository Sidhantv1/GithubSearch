package com.example.githubsearch.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GithubRepoDAO {

    @Insert
    suspend fun insertSubscriber(githubRepoDBDataClass: GithubRepoDBDataClass): Long

    @Query("SELECT * FROM data_table")
    fun getAllSubscribers(): LiveData<List<GithubRepoDBDataClass>>

}
package com.example.githubsearch

import com.example.githubsearch.modelClass.GithubSearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {

    @GET(ApiConstants.GET_DATA)
    fun getGithubRepositories(
        @Header("accept") accept: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<GithubSearchModel>

}
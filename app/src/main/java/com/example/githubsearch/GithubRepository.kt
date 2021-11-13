package com.example.githubsearch

import androidx.lifecycle.MutableLiveData
import com.example.githubsearch.modelClass.GithubSearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class
 */
class GithubRepository {

    // Live data of the Github Repository
    val githubRepositoriesLiveData = MutableLiveData<GithubSearchModel>()
    // Live data to observe api failure
    val githubRepositoriesApiFailureLiveData = MutableLiveData<Throwable>()

    /**
     * Retrofit code to get the response and errors
     */
    fun loadGithubRepositories(searchedRepository: String, page: Int, limit: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val api = RetrofitClientInstance.getRetrofitInstance().create(Api::class.java)

            // Api Request parameter passed in query
            val call = api.getGithubRepositories(
                accept = "application/vnd.github.v3+json",
                q = searchedRepository,
                page = page,
                per_page = limit
            )

            call.enqueue(object : Callback<GithubSearchModel> {
                // Failure Response
                override fun onFailure(call: Call<GithubSearchModel>, t: Throwable) {
                    githubRepositoriesApiFailureLiveData.value = t
                }

                // Success Response
                override fun onResponse(
                    call: Call<GithubSearchModel>,
                    response: Response<GithubSearchModel>
                ) {
                    githubRepositoriesLiveData.value = response.body()
                }
            })
        }

    }
}
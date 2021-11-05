package com.example.githubsearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubsearch.ModelClass.GithubSearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepositoriesViewModel(app: Application) : AndroidViewModel(app) {

    val githubRepositoriesLiveData = MutableLiveData<GithubSearchModel>()

    fun loadGithubRepositories(searchedRepository: String) {
        val api = RetrofitClientInstance.getRetrofitInstance().create(Api::class.java)

        val call = api.getGithubRepositories(
            accept = "application/vnd.github.v3+json",
            q = searchedRepository,
            page = 1,
            per_page = 10
        )

        call.enqueue(object : Callback<GithubSearchModel> {
            override fun onFailure(call: Call<GithubSearchModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<GithubSearchModel>,
                response: Response<GithubSearchModel>
            ) {
                githubRepositoriesLiveData.value = response.body()
            }
        })

    }

}
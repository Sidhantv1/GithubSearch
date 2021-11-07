package com.example.githubsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearch.db.SubscriberRepository

class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubViewModel::class.java)) {
            return GithubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
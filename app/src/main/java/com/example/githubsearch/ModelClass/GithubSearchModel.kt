package com.example.githubsearch.ModelClass

data class GithubSearchModel(
    var incomplete_results: Boolean,
    var items: List<Item>,
    var total_count: Int
)
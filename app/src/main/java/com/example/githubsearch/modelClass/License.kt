package com.example.githubsearch.modelClass

import java.io.Serializable

data class License(
    var key: String,
    var name: String,
    var node_id: String,
    var spdx_id: String,
    var url: String
): Serializable
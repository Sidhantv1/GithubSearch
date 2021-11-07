package com.example.githubsearch.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Data class of the db enteries
 */
@Entity(tableName = "data_table")
data class GithubRepoDBDataClass(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "repo_image")
    var repositoryImage: String?,

    @ColumnInfo(name = "repo_full_name")
    var repositoryFullName: String?,

    @ColumnInfo(name = "repo_prj_link")
    var projectLink: String?,

    @ColumnInfo(name = "repo_prj_contributor")
    var projectContributor: String?,

    @ColumnInfo(name = "repo_prj_description")
    var projectDescription: String?
) : Serializable
package com.example.githubsearch.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id: Int,

    @ColumnInfo(name = "subscriber_repo_full_name")
    var repositoryFullName: String?,

    @ColumnInfo(name = "subscriber_repo_prj_link")
    var projectLink: String?,

    @ColumnInfo(name = "subscriber_repo_prj_contributor")
    var projectContributor: String?,

    @ColumnInfo(name = "subscriber_repo_prj_description")
    var projectDescription: String?
) : Serializable
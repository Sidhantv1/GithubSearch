package com.example.githubsearch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubRepoDBDataClass::class], version = 1)
abstract class GithubRepoDataBase : RoomDatabase() {

    abstract val githubRepoDAO: GithubRepoDAO

    companion object {
        @Volatile
        private var INSTANCE: GithubRepoDataBase? = null

        fun getInstance(context: Context): GithubRepoDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubRepoDataBase::class.java,
                        "subscriber_data_database"
                    ).build()
                }
                return instance
            }
        }
    }

}
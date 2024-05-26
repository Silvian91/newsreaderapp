package com.example.newsreaderapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.database.model.ArticlesEntity

@Database(
    entities = [
        ArticlesEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase(): RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}

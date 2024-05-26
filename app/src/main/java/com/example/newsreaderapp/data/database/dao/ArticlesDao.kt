package com.example.newsreaderapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsreaderapp.data.database.model.ArticlesEntity


@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticlesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticlesEntity>)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}

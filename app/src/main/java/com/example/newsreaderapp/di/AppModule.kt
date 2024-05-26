package com.example.newsreaderapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsreaderapp.data.ResourceProvider
import com.example.newsreaderapp.data.database.AppDatabase
import com.example.newsreaderapp.data.database.dao.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Singleton
    fun providesBooksDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesBooksDao(
        database: AppDatabase
    ): ArticlesDao = database.articlesDao()
}
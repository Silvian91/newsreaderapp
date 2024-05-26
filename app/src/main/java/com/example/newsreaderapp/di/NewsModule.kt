package com.example.newsreaderapp.di

import com.example.newsreaderapp.data.GetNewsRepositoryImpl
import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.model.EntityToModelMapper
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.GetNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class NewsModule {

    @Provides
    fun provideGetNewsRepository(
        newsApi: NewsApi,
        articlesDao: ArticlesDao,
        entityToModelMapper: EntityToModelMapper,
        responseToEntityMapper: ResponseToEntityMapper,
        ): GetNewsRepository {
        return GetNewsRepositoryImpl(newsApi,articlesDao, entityToModelMapper, responseToEntityMapper)
    }

    @Provides
    fun provideEntityToModelMapper(): EntityToModelMapper {
        return EntityToModelMapper()
    }

    @Provides
    fun provideResponseToEntityMapper(): ResponseToEntityMapper {
        return ResponseToEntityMapper()
    }

}

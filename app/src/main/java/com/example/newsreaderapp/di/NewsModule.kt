package com.example.newsreaderapp.di

import com.example.newsreaderapp.data.GetNewsRepositoryImpl
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
    fun provideGetNewsRepository(newsApi: NewsApi): GetNewsRepository {
        return GetNewsRepositoryImpl(newsApi)
    }

}

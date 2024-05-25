package com.example.autolabschallenge.di

import com.example.autolabschallenge.data.GetNewsRepositoryImpl
import com.example.autolabschallenge.domain.NewsApi
import com.example.autolabschallenge.domain.repository.GetNewsRepository
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

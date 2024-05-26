package com.example.newsreaderapp.data

import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.GetNewsRepository

class GetNewsRepositoryImpl(
    private val newsApi: NewsApi
) : GetNewsRepository {
    override suspend fun getNews(): NewsModel {
        return newsApi.getNews().toModel()
    }
}
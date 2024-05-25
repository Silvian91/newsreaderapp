package com.example.autolabschallenge.data

import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.domain.NewsApi
import com.example.autolabschallenge.domain.repository.GetNewsRepository

class GetNewsRepositoryImpl(
    private val newsApi: NewsApi
) : GetNewsRepository {
    override suspend fun getNews(): NewsModel {
        return newsApi.getNews().toModel()
    }
}
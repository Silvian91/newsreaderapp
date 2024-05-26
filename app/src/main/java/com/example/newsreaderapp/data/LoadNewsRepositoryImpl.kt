package com.example.newsreaderapp.data

import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.LoadNewsRepository

class LoadNewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao,
    private val responseToEntityMapper: ResponseToEntityMapper
) : LoadNewsRepository {
    override suspend fun loadNews(): NewsModel {
        val response = newsApi.getNews()
        return if (response.status == "ok") {
            articlesDao.deleteAll()
            articlesDao.insertArticles(response.articles.map {
                responseToEntityMapper.map(it)
            })
            response.toModel()
        } else {
            throw Exception("Failed to fetch news")
        }
    }
}
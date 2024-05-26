package com.example.newsreaderapp.data

import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.model.EntityToModelMapper
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.GetNewsRepository
import kotlinx.coroutines.flow.firstOrNull

class GetNewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao,
    private val entityToModelMapper: EntityToModelMapper,
    private val responseToEntityMapper: ResponseToEntityMapper
) : GetNewsRepository {
    override suspend fun getNews(): NewsModel {
        val articlesFromDb = articlesDao.getArticles().firstOrNull()
        return if (!articlesFromDb.isNullOrEmpty()) {
            NewsModel("ok", articlesFromDb.map { entityToModelMapper.map(it) })
        } else {
            val response = newsApi.getNews()
            if (response.status == "ok") {
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
}

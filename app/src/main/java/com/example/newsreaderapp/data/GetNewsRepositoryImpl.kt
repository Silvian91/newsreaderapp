package com.example.newsreaderapp.data

import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.model.EntityToModelMapper
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.GetNewsRepository
import com.example.newsreaderapp.domain.repository.LoadNewsRepository
import kotlinx.coroutines.flow.firstOrNull

class GetNewsRepositoryImpl(
    private val articlesDao: ArticlesDao,
    private val entityToModelMapper: EntityToModelMapper,
    private val loadNewsRepository: LoadNewsRepository
) : GetNewsRepository {
    override suspend fun getNews(): NewsModel {
        val articlesFromDb = articlesDao.getArticles().firstOrNull()
        return if (!articlesFromDb.isNullOrEmpty()) {
            NewsModel("ok", articlesFromDb.map { entityToModelMapper.map(it) })
        } else {
            loadNewsRepository.loadNews()
        }
    }
}

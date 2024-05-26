package com.example.newsreaderapp.data.model

import com.example.newsreaderapp.data.database.model.ArticlesEntity
import com.example.newsreaderapp.data.response.ArticlesResponse
import javax.inject.Inject

class ResponseToEntityMapper@Inject constructor(): BaseMapper<ArticlesResponse, ArticlesEntity> {
    override fun map(from: ArticlesResponse): ArticlesEntity {
        return ArticlesEntity(
            source = from.source.name,
            author = from.author,
            title = from.title,
            description = from.description,
            url = from.url,
            urlToImage = from.urlToImage,
            publishedAt = from.publishedAt,
            content = from.content
        )
    }
}
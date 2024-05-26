package com.example.newsreaderapp.data.model

import com.example.newsreaderapp.data.database.model.ArticlesEntity
import javax.inject.Inject

class EntityToModelMapper @Inject constructor(): BaseMapper<ArticlesEntity, ArticlesModel> {
    override fun map(from: ArticlesEntity): ArticlesModel {
        return ArticlesModel(
            source = SourceModel(from.source),
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

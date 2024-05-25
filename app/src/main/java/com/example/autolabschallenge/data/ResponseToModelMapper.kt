package com.example.autolabschallenge.data

import com.example.autolabschallenge.data.model.ArticlesModel
import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.data.model.SourceModel
import com.example.autolabschallenge.data.response.ArticlesResponse
import com.example.autolabschallenge.data.response.NewsResponse
import com.example.autolabschallenge.data.response.SourceResponse

fun NewsResponse.toModel(): NewsModel {
    return NewsModel(
        status = this.status,
        articles = this.articles.map { it.toModel() }
    )
}

fun ArticlesResponse.toModel(): ArticlesModel {
    return ArticlesModel(
        source = this.source.toModel(),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}

fun SourceResponse.toModel(): SourceModel {
    return SourceModel(
        name = this.name
    )
}

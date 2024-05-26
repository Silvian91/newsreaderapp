package com.example.newsreaderapp.data

import com.example.newsreaderapp.data.model.ArticlesModel
import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.data.model.SourceModel
import com.example.newsreaderapp.data.response.ArticlesResponse
import com.example.newsreaderapp.data.response.NewsResponse
import com.example.newsreaderapp.data.response.SourceResponse

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

package com.example.autolabschallenge.data.model

data class NewsModel(
    val status: String,
    val articles: List<ArticlesModel>?
)

data class ArticlesModel(
    val source: SourceModel,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)


data class SourceModel(
    val name: String
)

package com.example.newsreaderapp.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("status") val status: String,
    @SerialName("articles") val articles: List<ArticlesResponse>
)

@Serializable
data class ArticlesResponse(
@SerialName("source") val source: SourceResponse,
@SerialName("author") val author: String?,
@SerialName("title") val title: String,
@SerialName("description") val description: String,
@SerialName("url") val url: String,
@SerialName("urlToImage") val urlToImage: String?,
@SerialName("publishedAt") val publishedAt: String,
@SerialName("content") val content: String
)

@Serializable
data class SourceResponse(
    @SerialName("name") val name: String
)

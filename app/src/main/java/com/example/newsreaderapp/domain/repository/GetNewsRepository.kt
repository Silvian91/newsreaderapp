package com.example.newsreaderapp.domain.repository

import com.example.newsreaderapp.data.model.NewsModel

interface GetNewsRepository {
    suspend fun getNews(): NewsModel
}
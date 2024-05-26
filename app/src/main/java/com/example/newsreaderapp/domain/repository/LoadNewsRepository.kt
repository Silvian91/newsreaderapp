package com.example.newsreaderapp.domain.repository

import com.example.newsreaderapp.data.model.NewsModel

interface LoadNewsRepository {
    suspend fun loadNews(): NewsModel
}
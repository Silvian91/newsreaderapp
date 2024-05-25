package com.example.autolabschallenge.domain.repository

import com.example.autolabschallenge.data.model.NewsModel

interface GetNewsRepository {
    suspend fun getNews(): NewsModel
}
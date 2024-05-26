package com.example.newsreaderapp.domain

import com.example.newsreaderapp.BuildConfig
import com.example.newsreaderapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("Authorization: ${BuildConfig.NEWS_API_KEY}")
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String = "keyword"
    ): NewsResponse

}

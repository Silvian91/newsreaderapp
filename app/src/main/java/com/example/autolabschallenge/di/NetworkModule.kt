package com.example.autolabschallenge.di

import com.example.autolabschallenge.domain.BASEURL
import com.example.autolabschallenge.domain.NewsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        networkJson: Json,
        okhttpCallFactory: Call.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}

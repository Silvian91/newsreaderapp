package com.example.newsreaderapp.domain.useCase

import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.repository.GetNewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val getNewsRepository: GetNewsRepository
)  {
    suspend operator fun invoke(): NewsModel {
        return getNewsRepository.getNews()
    }
}

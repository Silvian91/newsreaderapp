package com.example.newsreaderapp.domain.useCase

import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.repository.LoadNewsRepository
import javax.inject.Inject

class LoadNewsUseCase @Inject constructor(
    private val loadNewsRepository: LoadNewsRepository
)  {
    suspend operator fun invoke(): NewsModel {
        return loadNewsRepository.loadNews()
    }
}

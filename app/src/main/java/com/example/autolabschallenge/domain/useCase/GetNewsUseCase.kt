package com.example.autolabschallenge.domain.useCase

import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.domain.repository.GetNewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val getNewsRepository: GetNewsRepository
)  {
    suspend operator fun invoke(): NewsModel {
        return getNewsRepository.getNews()
    }
}

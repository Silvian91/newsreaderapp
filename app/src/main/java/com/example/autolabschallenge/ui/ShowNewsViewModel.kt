package com.example.autolabschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autolabschallenge.data.model.ArticlesModel
import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.data.model.SourceModel
import com.example.autolabschallenge.domain.useCase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    val defaultNewsModel = NewsModel(
        status = "",
        articles = emptyList()
    )

    val defaultArticlesModel = ArticlesModel(
        source = SourceModel(""),
        author = null,
        title = "",
        description = "",
        url = "",
        urlToImage = null,
        publishedAt = "",
        content = ""
    )

    private val _newsList = MutableStateFlow<NewsModel>(defaultNewsModel)
    val newsList: StateFlow<NewsModel> get() = _newsList.asStateFlow()

    private val _newsArticle = MutableStateFlow<ArticlesModel>(defaultArticlesModel)
    val newsArticle: StateFlow<ArticlesModel> get() = _newsArticle

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()


    private val _voiceCommand = MutableStateFlow<String?>(null)
    val voiceCommand: StateFlow<String?> get() = _voiceCommand

    suspend fun getNews() {
        _isLoading.value = true
        _newsList.value = getNewsUseCase()
        _isLoading.value = false
    }

    fun setArticle(article: ArticlesModel) {
        _newsArticle.value = article
    }

    fun setVoiceCommand(voiceCommand: String) {
        viewModelScope.launch {
            _voiceCommand.value = voiceCommand
            if (voiceCommand.equals("reload", ignoreCase = true)) {
                getNews()
            }
        }
    }

}

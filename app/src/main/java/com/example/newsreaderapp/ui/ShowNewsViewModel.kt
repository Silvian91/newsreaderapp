package com.example.newsreaderapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreaderapp.R
import com.example.newsreaderapp.data.ResourceProvider
import com.example.newsreaderapp.data.model.ArticlesModel
import com.example.newsreaderapp.data.model.NewsModel
import com.example.newsreaderapp.domain.useCase.GetNewsUseCase
import com.example.newsreaderapp.domain.useCase.LoadNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowNewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val loadNewsUseCase: LoadNewsUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _newsList = MutableStateFlow<NewsModel?>(null)
    val newsList: StateFlow<NewsModel?> get() = _newsList

    private val _newsArticle = MutableStateFlow<ArticlesModel?>(null)
    val newsArticle: StateFlow<ArticlesModel?> get() = _newsArticle

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    suspend fun getNews(isFromVoiceCommand: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _newsList.value = if (isFromVoiceCommand) {
                    loadNewsUseCase()
                } else {
                    getNewsUseCase()
                }
            } catch (e: Exception) {
                _errorState.value = resourceProvider.getString(R.string.error_internet_connection)
            }
            _isLoading.value = false
        }
    }

    fun setArticle(article: ArticlesModel) {
        _newsArticle.value = article
    }

    fun setVoiceCommand(voiceCommand: String, isFromVoiceCommand: Boolean) {
        viewModelScope.launch {
            if (voiceCommand.equals("reload", ignoreCase = true)) {
                getNews(isFromVoiceCommand)
            }
        }
    }
//
    fun sortNews() {
        _newsList.value = _newsList.value?.copy(
            articles = _newsList.value!!.articles?.sortedBy { it.publishedAt }
        )
    }
}

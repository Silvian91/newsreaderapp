package com.example.autolabschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autolabschallenge.R
import com.example.autolabschallenge.data.ResourceProvider
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
    private val getNewsUseCase: GetNewsUseCase,
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

    private val _voiceCommand = MutableStateFlow<String?>(null)
    val voiceCommand: StateFlow<String?> get() = _voiceCommand

    suspend fun getNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _newsList.value = getNewsUseCase()
            } catch (e: Exception) {
                _errorState.value = resourceProvider.getString(R.string.error_internet_connection)
            }
            _isLoading.value = false
        }
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

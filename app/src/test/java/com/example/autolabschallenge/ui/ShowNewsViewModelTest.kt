package com.example.autolabschallenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.autolabschallenge.R
import com.example.autolabschallenge.data.ResourceProvider
import com.example.autolabschallenge.data.model.ArticlesModel
import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.data.model.SourceModel
import com.example.autolabschallenge.domain.useCase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ShowNewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var showNewsViewModel: ShowNewsViewModel

    @Mock
    private lateinit var getNewsUseCase: GetNewsUseCase

    @Mock
    private lateinit var resourceProvider: ResourceProvider


    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        showNewsViewModel = ShowNewsViewModel(getNewsUseCase, resourceProvider)
    }

    @Test
    fun `getNews should update newsList on success`() = runTest {
        val articles = listOf(ArticlesModel(SourceModel("some source"), "author","title", "description", "url", "urlToImage", "publishedAt", "content"))
        val newsModel = NewsModel("ok", articles)

        `when`(getNewsUseCase()).thenReturn(newsModel)

        showNewsViewModel.getNews()

        assertEquals(newsModel, showNewsViewModel.newsList.value)
    }

    @Test
    fun `getNews should update errorState on failure`() = runTest {
        val errorMessage = "Failed to fetch news. Please check your internet connection."
        `when`(getNewsUseCase()).thenThrow(RuntimeException("Network error"))
        `when`(resourceProvider.getString(R.string.error_internet_connection)).thenReturn(errorMessage)

        showNewsViewModel.getNews()

        assertEquals(errorMessage, showNewsViewModel.errorState.value)
        assertFalse(showNewsViewModel.isLoading.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

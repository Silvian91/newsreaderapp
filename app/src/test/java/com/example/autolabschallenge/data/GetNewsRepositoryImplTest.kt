package com.example.autolabschallenge.data

import com.example.autolabschallenge.data.model.NewsModel
import com.example.autolabschallenge.data.response.NewsResponse
import com.example.autolabschallenge.domain.NewsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetNewsRepositoryImplTest {

    @Mock
    private lateinit var newsApiService: NewsApi

    private lateinit var getNewsRepository: GetNewsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getNewsRepository = GetNewsRepositoryImpl(newsApiService)
    }

    @Test
    fun `getNews should return NewsModel on success`() = runTest {
        val newsResponse = NewsResponse("ok", emptyList())
        val newsModel = NewsModel("ok", emptyList())

        `when`(newsApiService.getNews()).thenReturn(newsResponse)

        val result = getNewsRepository.getNews()

        assertEquals(newsModel, result)
    }

    @Test(expected = RuntimeException::class)
    fun `getNews should throw exception on failure`() = runTest {
        `when`(newsApiService.getNews()).thenThrow(RuntimeException("Network error"))

        getNewsRepository.getNews()
    }
}

package com.example.newsreaderapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.database.model.ArticlesEntity
import com.example.newsreaderapp.data.model.EntityToModelMapper
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.data.response.NewsResponse
import com.example.newsreaderapp.domain.NewsApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class LoadNewsRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articlesDao: ArticlesDao
    private lateinit var newsApi: NewsApi
    private lateinit var responseToEntityMapper: ResponseToEntityMapper
    private lateinit var repository: LoadNewsRepositoryImpl
    private val entityToModelMapper: EntityToModelMapper = mock(EntityToModelMapper::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        articlesDao = mock(ArticlesDao::class.java)
        newsApi = mock(NewsApi::class.java)
        responseToEntityMapper = mock(ResponseToEntityMapper::class.java)

        repository = LoadNewsRepositoryImpl(
            newsApi,
            articlesDao,
            responseToEntityMapper
        )
    }

    @Test
    fun `loadNews fetches from database when available`() = runTest {
        val mockArticlesEntity = listOf(mock(ArticlesEntity::class.java))
        `when`(articlesDao.getArticles()).thenReturn(flowOf(mockArticlesEntity))

        val mockApiResponse = mock(NewsResponse::class.java)
        `when`(mockApiResponse.status).thenReturn("ok")
        `when`(mockApiResponse.articles).thenReturn(emptyList())
        `when`(newsApi.getNews()).thenReturn(mockApiResponse)

        val result = repository.loadNews()

        verify(articlesDao, times(1)).getArticles()
        assertEquals(mockArticlesEntity.map { entityToModelMapper.map(it) }, result.articles)
    }

    @Test
    fun `loadNews fetches from API when database is empty`() = runTest {
        val mockResponse = mock(NewsResponse::class.java)
        `when`(articlesDao.getArticles()).thenReturn(flowOf(emptyList()))
        `when`(newsApi.getNews()).thenReturn(mockResponse)
        `when`(mockResponse.status).thenReturn("ok")

        val result = repository.loadNews()

        verify(newsApi, times(1)).getNews()
        verify(articlesDao, times(1)).deleteAll()
        verify(articlesDao, times(1)).insertArticles(anyList())
        assertEquals(mockResponse.status, result.status)
    }

    @Test(expected = Exception::class)
    fun `loadNews throws exception when API call fails`() = runTest {
        `when`(articlesDao.getArticles()).thenReturn(flowOf(emptyList()))
        `when`(newsApi.getNews()).thenThrow(RuntimeException("Network error"))

        repository.loadNews()
    }
}

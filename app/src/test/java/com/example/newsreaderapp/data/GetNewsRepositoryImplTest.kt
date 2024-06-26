package com.example.newsreaderapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsreaderapp.data.database.dao.ArticlesDao
import com.example.newsreaderapp.data.database.model.ArticlesEntity
import com.example.newsreaderapp.data.model.EntityToModelMapper
import com.example.newsreaderapp.data.model.ResponseToEntityMapper
import com.example.newsreaderapp.data.response.NewsResponse
import com.example.newsreaderapp.domain.NewsApi
import com.example.newsreaderapp.domain.repository.LoadNewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetNewsRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articlesDao: ArticlesDao
    private lateinit var newsApi: NewsApi
    private lateinit var entityToModelMapper: EntityToModelMapper
    private lateinit var responseToEntityMapper: ResponseToEntityMapper
    private lateinit var repository: GetNewsRepositoryImpl
    private lateinit var loadNewsRepository: LoadNewsRepository
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        articlesDao = mock(ArticlesDao::class.java)
        newsApi = mock(NewsApi::class.java)
        entityToModelMapper = mock(EntityToModelMapper::class.java)
        responseToEntityMapper = mock(ResponseToEntityMapper::class.java)
        loadNewsRepository = mock(LoadNewsRepository::class.java)

        repository = GetNewsRepositoryImpl(
            articlesDao,
            entityToModelMapper,
            loadNewsRepository
        )
    }

    @Test
    fun `getNews fetches from database when available`() = runTest {
        val mockEntity = listOf(mock(ArticlesEntity::class.java))
        `when`(articlesDao.getArticles()).thenReturn(flowOf(mockEntity))

        val result = repository.getNews()

        verify(articlesDao, times(1)).getArticles()
        assertEquals(mockEntity, result.articles)
    }

    @Test
    fun `getNews fetches from API when database is empty`() = runTest {
        val mockResponse = mock(NewsResponse::class.java)
        `when`(articlesDao.getArticles()).thenReturn(flowOf(emptyList()))
        `when`(newsApi.getNews()).thenReturn(mockResponse)
        `when`(mockResponse.status).thenReturn("ok")

        val result = repository.getNews()

        verify(newsApi, times(1)).getNews()
        verify(articlesDao, times(1)).deleteAll()
        verify(articlesDao, times(1)).insertArticles(anyList())
        assertEquals(mockResponse.status, result.status)
    }

    @Test(expected = Exception::class)
    fun `getNews throws exception when API call fails`() = runTest {
        `when`(articlesDao.getArticles()).thenReturn(flowOf(emptyList()))
        `when`(newsApi.getNews()).thenThrow(RuntimeException("Network error"))

        repository.getNews()
    }
}

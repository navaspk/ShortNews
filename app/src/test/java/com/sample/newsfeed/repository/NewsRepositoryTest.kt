package com.sample.newsfeed.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sample.newsfeed.layers.domain.api.NewsServiceApi
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.newsfeed.layers.data.repository.NewsRepository
import com.sample.newsfeed.layers.framework.NewsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

//@RunWith(JUnit4::class)
class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private val service = mock(NewsServiceApi::class.java)
    private val remoteDataSource = NewsSearchListUseCase(service)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = NewsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun loadNewsFromNetwork() {
        runBlocking {
            val data = repository.getNyArticleList(coroutineScope = coroutineScope)
            Truth.assertThat(data).isNotNull()
        }
    }
}

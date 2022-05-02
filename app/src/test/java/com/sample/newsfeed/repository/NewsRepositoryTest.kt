package com.sample.newsfeed.repository

import com.sample.newsfeed.layers.domain.api.NewsServiceApi
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.newsfeed.layers.data.repository.NewsRepository
import com.sample.newsfeed.layers.framework.NewsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private val service = mock(NewsServiceApi::class.java)
    private val remoteDataSource = NewsSearchListUseCase(service)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        repository = NewsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun loadNewsFromNetwork() {
        runBlocking {
            repository.getNyArticleList(coroutineScope = coroutineScope)

            verifyZeroInteractions(repository)
        }
    }
}

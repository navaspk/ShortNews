package com.sample.newsfeed.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.newsfeed.layers.data.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class NewsViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(NewsRepository::class.java)

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).getNyArticleList(coroutineScope)
    }

}

package com.sample.newsfeed.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.newsfeed.layers.framework.NewsRepositoryImpl
import com.sample.newsfeed.layers.data.repository.NewsRepository
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.core.supporter.GsonProvider
import com.sample.newsfeed.layers.domain.api.NewsServiceApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat(GsonProvider.ISO_8601_DATE_FORMAT).create()
    }

    @Provides
    @Singleton
    fun provideGsonProvider(): GsonProvider = GsonProvider()

    @Provides
    @Singleton
    fun provideNewsSearchListUseCase(
        newsService: NewsServiceApi
    ) = NewsSearchListUseCase(newsService)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNewsDataRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}

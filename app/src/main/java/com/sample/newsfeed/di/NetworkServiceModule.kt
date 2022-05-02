package com.sample.newsfeed.di

import com.sample.core.supporter.GsonProvider
import com.sample.core.supporter.NetworkUtil
import com.sample.news.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideNetworkService(
        gsonProvider: GsonProvider
    ) = NetworkUtil(
        gsonProvider = gsonProvider,
        endPoint = BuildConfig.BASE_URL
    )
}

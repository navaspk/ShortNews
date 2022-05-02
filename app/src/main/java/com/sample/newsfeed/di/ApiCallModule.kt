package com.sample.newsfeed.di

import com.sample.newsfeed.layers.domain.api.NewsServiceApi
import com.sample.core.supporter.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiCallModule {

    @Provides
    fun provideArticleService(networkUtil: NetworkUtil) =
        networkUtil.create(NewsServiceApi::class.java)
}

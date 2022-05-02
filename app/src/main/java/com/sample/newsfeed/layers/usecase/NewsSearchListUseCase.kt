package com.sample.newsfeed.layers.usecase

import com.sample.core.layers.usecase.MainUseCase
import com.sample.news.BuildConfig
import com.sample.newsfeed.layers.domain.api.NewsServiceApi
import com.sample.newsfeed.layers.domain.model.ItemsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsSearchListUseCase @Inject constructor(
    private val apiService: NewsServiceApi
) : MainUseCase<ItemsResponse, NewsSearchListUseCase.Params>() {

    override suspend fun createUseCase(params: Params?): Response<ItemsResponse> {
        return apiService.fetchArticles(page = params?.page ?: 0)
    }

    data class Params constructor(val apiKey: String = BuildConfig.API_KEY, val page: Int)
}

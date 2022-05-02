package com.sample.newsfeed.layers.domain.api

import com.sample.news.BuildConfig
import com.sample.newsfeed.layers.domain.model.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServiceApi {

    @GET("svc/search/v2/articlesearch.json")
    suspend fun fetchArticles(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") source: String? = "Dubai",
        @Query("page") page : Int = 0
    ): Response<ItemsResponse>

}

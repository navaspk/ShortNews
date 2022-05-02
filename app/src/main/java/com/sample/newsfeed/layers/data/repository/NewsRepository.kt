package com.sample.newsfeed.layers.data.repository

import com.sample.newsfeed.layers.domain.api.Data
import com.sample.newsfeed.layers.domain.model.DocsItem
import kotlinx.coroutines.CoroutineScope

interface NewsRepository {

    fun getNyArticleList(
        coroutineScope: CoroutineScope
    ): Data<DocsItem>

}

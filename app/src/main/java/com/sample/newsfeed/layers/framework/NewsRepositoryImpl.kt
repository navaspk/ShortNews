package com.sample.newsfeed.layers.framework

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.sample.newsfeed.layers.domain.api.Data
import com.sample.newsfeed.layers.data.repository.NewsRepository
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.newsfeed.layers.domain.model.DocsItem
import com.sample.newsfeed.layers.data.datasource.NewsPageDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val useCase: NewsSearchListUseCase
) : NewsRepository {

    override fun getNyArticleList(
        coroutineScope: CoroutineScope
    ): Data<DocsItem> {
        val dataSourceFactory = NewsPageDataSourceFactory(
            useCase,
            coroutineScope
        )

        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }
        return Data(
            LivePagedListBuilder(
                dataSourceFactory,
                NewsPageDataSourceFactory.pagedListConfig()
            ).build(), networkState
        )
    }
}

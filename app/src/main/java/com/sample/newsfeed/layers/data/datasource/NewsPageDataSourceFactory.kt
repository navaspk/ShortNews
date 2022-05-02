package com.sample.newsfeed.layers.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.newsfeed.layers.domain.model.DocsItem
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class NewsPageDataSourceFactory @Inject constructor(
    private val dataSource: NewsSearchListUseCase,
    private val scope: CoroutineScope

) : DataSource.Factory<Int, DocsItem>() {

    val liveData = MutableLiveData<NewsPageDataSource>()

    override fun create(): DataSource<Int, DocsItem> {

        val source = NewsPageDataSource(dataSource, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}

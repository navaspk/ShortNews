package com.sample.newsfeed.layers.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sample.core.layers.controller.ItemResult
import com.sample.newsfeed.layers.domain.api.NetworkState
import com.sample.newsfeed.layers.usecase.NewsSearchListUseCase
import com.sample.newsfeed.layers.domain.model.DocsItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsPageDataSource @Inject constructor(
    private val remoteDataSource: NewsSearchListUseCase,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, DocsItem>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DocsItem>
    ) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(page = 1) {
            it?.let { it1 -> callback.onResult(it1, null, 2) }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DocsItem>) {
        networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(page = page) {
            it?.let { it1 -> callback.onResult(it1, page + 1) }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DocsItem>) {
        val page = params.key
        fetchData(page) {
            it?.let { it1 -> callback.onResult(it1, page - 1) }
        }
    }

    private fun fetchData(page: Int, callback: (List<DocsItem?>?) -> Unit) {
        coroutineScope.launch(getJobErrorHandler()) {
            when (val response = remoteDataSource.execute(NewsSearchListUseCase.Params(page = page))) {
                is ItemResult.Error -> {
                    networkState.postValue(NetworkState.ERROR(response.message ?: "Unknown error"))
                    postError(response.message)
                }
                is ItemResult.Success -> {
                    val results = response.data.response?.docs
                    callback(results)
                    networkState.postValue(NetworkState.LOADED)
                }
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String?) {
        Log.e("NewsPageDataSource","An error happened: $message")
    }
}

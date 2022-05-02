package com.sample.newsfeed.layers.domain.api

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Data class that is necessary for a UI to show a listing and interact
 */
data class Data<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>
)

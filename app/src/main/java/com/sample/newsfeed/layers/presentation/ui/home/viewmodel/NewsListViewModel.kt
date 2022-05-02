package com.sample.newsfeed.layers.presentation.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.sample.newsfeed.base.BaseNavigator
import com.sample.newsfeed.base.BaseViewModel
import com.sample.newsfeed.layers.domain.api.Data
import com.sample.newsfeed.layers.domain.model.DocsItem
import com.sample.newsfeed.layers.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * VM class responsible for making API call and getting response & update the UI using LiveData
 */
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel<BaseNavigator>() {

    // region VARIABLES

    val userIntent = Channel<NewsIntent>(Channel.UNLIMITED)
    var searchedNewsList: Data<DocsItem>? = null

    init {
        handleNewsIntent()
    }

    // endregion

    // region UTIL

    private fun handleNewsIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is NewsIntent.GetSearchedNews ->
                        getPopularNews()
                }
            }
        }
    }

    private fun getPopularNews() {

        if (searchedNewsList == null) {
            searchedNewsList = repository.getNyArticleList(viewModelScope)
        }

        //return searchedNewsList
    }

    // endregion


    sealed class NewsIntent {
        object GetSearchedNews : NewsIntent()
    }
}

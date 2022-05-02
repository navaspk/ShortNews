package com.sample.newsfeed.layers.domain.api

sealed class NetworkState {
    object LOADED : NetworkState()
    object LOADING : NetworkState()
    data class ERROR(val msg: String): NetworkState()
}

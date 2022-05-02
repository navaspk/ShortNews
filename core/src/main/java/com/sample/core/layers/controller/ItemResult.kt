package com.sample.core.layers.controller

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

sealed class ItemResult<out T> {
    data class Success<out T>(val data : T) : ItemResult<T>()
    data class Error<out T>(val message: String? = "Unknown error", val data: T? = null): ItemResult<T>()
}

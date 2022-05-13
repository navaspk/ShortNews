package com.sample.newsfeed.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Utility file for performing all utility generic operation with extension function which belongs
 * to specific object.
 */
fun View.showSnackBar(str: String) {
    Snackbar.make(
        this,
        str,
        Snackbar.LENGTH_LONG
    ).show()
}

fun Context.hasInternetConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return try {
        val activeNetwork =
            cm?.activeNetworkInfo
        activeNetwork != null && (activeNetwork.isConnected || activeNetwork.type == ConnectivityManager.TYPE_ETHERNET)
    } catch (e: RuntimeException) {
        true
    }
}

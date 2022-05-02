package com.sample.newsfeed

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShortNewsApp : Application() {

    companion object {
        lateinit var instance: ShortNewsApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}

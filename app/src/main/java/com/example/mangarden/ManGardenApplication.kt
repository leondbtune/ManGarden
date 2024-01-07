package com.example.mangarden

import android.app.Application
import com.example.mangarden.data.AppContainer
import com.example.mangarden.data.DefaultAppContainer

/**
 * ManGardenApplication is the application class of the app.
 */
class ManGardenApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = this)
    }
}
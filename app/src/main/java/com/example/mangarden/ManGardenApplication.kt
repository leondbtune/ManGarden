package com.example.mangarden

import android.app.Application
import com.example.mangarden.data.AppContainer
import com.example.mangarden.data.DefaultAppContainer

class ManGardenApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = this)
    }
}
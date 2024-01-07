package com.example.mangarden.data

import android.content.Context
import com.example.mangarden.ManGardenApplication
import com.example.mangarden.data.database.MangaDatabase
import com.example.mangarden.network.MangaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * This is a container for all the dependencies in the app.
 */
interface AppContainer {
    val mangaRepository: MangaRepository
}

/**
 * Implementation for Dependency Injection
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://consumet-api-one-pi.vercel.app/manga/mangadex/"
    val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Retrofit builder to build object using kotlinx.serialization converter
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: MangaApiService by lazy {
        retrofit.create(MangaApiService::class.java)
    }

    /**
     * Dependency injection for MangaRepository
     */
    override val mangaRepository: MangaRepository by lazy {
        NetworkMangaRepository(
            retrofitService,
            MangaDatabase.getInstance(context).mangaDao()
        )
    }
}
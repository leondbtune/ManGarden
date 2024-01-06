package com.example.mangarden.network

import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MangaApiService {
    @GET("{query}")
    suspend fun getMangaList(@Path("query") query: String): MangaResultModel

    @GET("info/{mangaId}")
    suspend fun getMangaDetail(@Path("mangaId") mangaId: String): MangaDetailModel
}
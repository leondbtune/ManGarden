package com.example.mangarden.data

import com.example.mangarden.model.MangaModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.network.MangaApiService

interface MangaRepository {
    suspend fun getMangaList(): List<MangaResultModel>
}

class NetworkMangaRepository(
    private val api: MangaApiService
) : MangaRepository {
    override suspend fun getMangaList(): List<MangaResultModel> = api.getMangaList()
}
package com.example.mangarden.data

import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.network.MangaApiService

interface MangaRepository {
    suspend fun getMangaList(query: String): MangaResultModel
    suspend fun getMangaDetail(mangaId: String): MangaDetailModel
}

class NetworkMangaRepository(
    private val api: MangaApiService
) : MangaRepository {
    override suspend fun getMangaList(query: String): MangaResultModel = api.getMangaList(query)

    override suspend fun getMangaDetail(mangaId: String): MangaDetailModel = api.getMangaDetail(mangaId)
}
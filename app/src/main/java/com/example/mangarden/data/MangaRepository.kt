package com.example.mangarden.data

import com.example.mangarden.data.database.MangaDao
import com.example.mangarden.data.database.MangaEntity
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.network.MangaApiService

interface MangaRepository {
    suspend fun getMangaList(query: String): MangaResultModel
    suspend fun getMangaDetail(mangaId: String): MangaDetailModel
    suspend fun getMangaListFromDatabase(): List<MangaDetailModel>
    suspend fun insertManga(mangaId: String, chaptersRead: Int = 0)
    suspend fun deleteManga(mangaId: String)
    suspend fun updateManga(mangaId: String, chaptersRead: Int)
    fun getChaptersRead(highLightedMangaId: String): Int
}

class NetworkMangaRepository(
    private val api: MangaApiService,
    private val database: MangaDao
) : MangaRepository {
    override suspend fun getMangaList(query: String): MangaResultModel = api.getMangaList(query)

    override suspend fun getMangaDetail(mangaId: String): MangaDetailModel = api.getMangaDetail(mangaId)
    override suspend fun getMangaListFromDatabase(): List<MangaDetailModel> {
        return database.getMangaList().map {
            api.getMangaDetail(it.id)
        }
    }

    override suspend fun insertManga(mangaId: String, chaptersRead: Int) {
        database.insertManga(MangaEntity(mangaId, chaptersRead))
    }

    override suspend fun deleteManga(mangaId: String) {
        database.deleteManga(mangaId)
    }

    override fun getChaptersRead(highLightedMangaId: String): Int {
        return database.getChaptersRead(highLightedMangaId)
    }

    override suspend fun updateManga(mangaId: String, chaptersRead: Int) {
        database.updateManga(mangaId, chaptersRead)
    }


}
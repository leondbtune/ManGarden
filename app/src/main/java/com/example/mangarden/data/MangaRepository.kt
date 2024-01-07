package com.example.mangarden.data

import com.example.mangarden.data.database.MangaDao
import com.example.mangarden.data.database.MangaEntity
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.network.MangaApiService

/*
* Repository for manga
 */
interface MangaRepository {
    suspend fun getMangaList(query: String): MangaResultModel
    suspend fun getMangaDetail(mangaId: String): MangaDetailModel
    suspend fun getMangaListFromDatabase(): List<MangaDetailModel>
    suspend fun insertManga(mangaId: String, chaptersRead: Int = 0)
    suspend fun deleteManga(mangaId: String)
    suspend fun updateManga(mangaId: String, chaptersRead: Int)
    fun getChaptersRead(highLightedMangaId: String): Int
}

/**
* Network repository for mangadex
 */
class NetworkMangaRepository(
    private val api: MangaApiService,
    private val database: MangaDao
) : MangaRepository {

    /**
    * Get manga list from api
     */
    override suspend fun getMangaList(query: String): MangaResultModel = api.getMangaList(query)

    /**
    * Get manga detail from api
     */

    override suspend fun getMangaDetail(mangaId: String): MangaDetailModel = api.getMangaDetail(mangaId)

    /**
    * Get manga list from database and convert to manga detail model
     */
    override suspend fun getMangaListFromDatabase(): List<MangaDetailModel> {
        return database.getMangaList().map {
            api.getMangaDetail(it.id)
        }
    }

    /**
     * Insert manga to database
     */
    override suspend fun insertManga(mangaId: String, chaptersRead: Int) {
        database.insertManga(MangaEntity(mangaId, chaptersRead))
    }

    /**
     * Delete manga from database
     */
    override suspend fun deleteManga(mangaId: String) {
        database.deleteManga(mangaId)
    }

    /**
     * Get amount of chapters read from database
     */
    override fun getChaptersRead(highLightedMangaId: String): Int {
        return database.getChaptersRead(highLightedMangaId)
    }

    /**
     * Update amount of chapters read in database
     */
    override suspend fun updateManga(mangaId: String, chaptersRead: Int) {
        database.updateManga(mangaId, chaptersRead)
    }


}
package com.example.mangarden.test.fakers

import com.example.mangarden.data.MangaRepository
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel

class FakeRepository : MangaRepository {
    override suspend fun getMangaList(query: String): MangaResultModel {
        return FakeDataSource.searchResult
    }

    override suspend fun getMangaDetail(mangaId: String): MangaDetailModel {
        TODO("Not yet implemented")
    }

    override suspend fun getMangaListFromDatabase(): List<MangaDetailModel> {
        return FakeDataSource.databaseMangaList
    }

    override suspend fun insertManga(mangaId: String, chaptersRead: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteManga(mangaId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateManga(mangaId: String, chaptersRead: Int) {
        // Do nothing
    }

    override fun getChaptersRead(highLightedMangaId: String): Int {
        TODO("Not yet implemented")
    }
}
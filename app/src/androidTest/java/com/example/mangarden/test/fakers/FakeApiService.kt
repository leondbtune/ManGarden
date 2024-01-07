package com.example.mangarden.test.fakers

import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.network.MangaApiService

class FakeApiService : MangaApiService {
    override suspend fun getMangaList(query: String): MangaResultModel {
        return FakeDataSource.searchResult
    }

    override suspend fun getMangaDetail(mangaId: String): MangaDetailModel {
        TODO("Not yet implemented")
    }

}
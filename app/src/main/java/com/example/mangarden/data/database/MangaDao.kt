package com.example.mangarden.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MangaDao {
    @Insert
    suspend fun insertManga(manga: MangaEntity)

    @Query("UPDATE manga SET chaptersRead = :chaptersRead WHERE id = :id")
    suspend fun updateManga(id: String, chaptersRead: Int)

    @Query("DELETE FROM manga WHERE id = :id")
    suspend fun deleteManga(id: String)

    @Query("SELECT * FROM manga")
    suspend fun getMangaList(): List<MangaEntity>

    @Query("SELECT chaptersRead FROM manga WHERE id = :highLightedMangaId")
    abstract fun getChaptersRead(highLightedMangaId: String): Int
}
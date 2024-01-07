package com.example.mangarden.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MangaDao {
    @Insert
    suspend fun insertManga(manga: MangaEntity)

    @Update
    suspend fun updateManga(manga: MangaEntity)

    @Query("SELECT * FROM manga")
    suspend fun getMangaList(): List<MangaEntity>
}
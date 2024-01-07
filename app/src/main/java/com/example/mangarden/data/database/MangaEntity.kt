package com.example.mangarden.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity (
    @PrimaryKey
    val id: String,
    val chaptersRead: Int,
)
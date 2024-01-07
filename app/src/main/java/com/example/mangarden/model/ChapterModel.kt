package com.example.mangarden.model

import kotlinx.serialization.Serializable

/**
 * Model for chapters
 */
@Serializable
data class ChapterModel(
    val id: String,
    val title: String = "",
    val chapterNumber: String = "",
    val volumeNumber: String  = "",
    val pages: Int = 0,
)


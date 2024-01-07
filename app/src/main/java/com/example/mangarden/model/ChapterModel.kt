package com.example.mangarden.model

import kotlinx.serialization.Serializable

@Serializable
data class ChapterModel(
    val id: String,
    val title: String = "",
    val chapterNumber: String = "",
    val volumeNumber: String  = "",
    val pages: Int = 0,
)

data class ChapterDetailModel(
    val pages: List<PageModel>
)
package com.example.mangarden.model

import kotlinx.serialization.Serializable

@Serializable
data class ChapterModel(
    val id: String,
    val title: String,
    val releaseDate: String = "",
)

data class ChapterDetailModel(
    val pages: List<PageModel>
)
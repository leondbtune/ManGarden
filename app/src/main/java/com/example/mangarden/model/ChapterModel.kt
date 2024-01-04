package com.example.mangarden.model

data class ChapterModel(
    val id: String,
    val title: String,
    val releaseDate: String
)

data class ChapterDetailModel(
    val pages: List<PageModel>
)
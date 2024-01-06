package com.example.mangarden.model

import kotlinx.serialization.Serializable

@Serializable
data class MangaModel(
    val id: String,
    val title: String,
    val description: String? = "",
    val status: String? = "",
    val releaseDate: Int?,
    val contentRating: String?,
    val lastVolume: String?,
    val lastChapter: String?,
)

@Serializable
data class MangaDetailModel(
    val id: String,
    val title: String,
    val description: descriptionLangs = descriptionLangs(""),
    val genres: List<String> = listOf(),
    val status: String = "",
    val releaseDate: Int? = 0,
    val chapters: List<ChapterModel> = listOf(),

)

@Serializable
data class MangaResultModel(
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val results: List<MangaModel>,
)

@Serializable
data class descriptionLangs(
    val en: String,
)
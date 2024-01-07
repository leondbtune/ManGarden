package com.example.mangarden.model

import kotlinx.serialization.Serializable

/**
 * Model for manga
 */
@Serializable
data class MangaModel(
    val id: String,
    val title: String,
    val description: String? = "",
    val status: String? = "",
    val releaseDate: Int? = 0,
    val contentRating: String? = "",
    val lastVolume: String? = "",
    val lastChapter: String? = "",
)

/**
 * Model for manga detail
 */
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

/**
 * Model for manga result
 */
@Serializable
data class MangaResultModel(
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val results: List<MangaModel>,
)

/**
 * Model for description langs as support for manga detail
 */
@Serializable
data class descriptionLangs(
    val en: String,
)
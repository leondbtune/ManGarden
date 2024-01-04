package com.example.mangarden.model

data class MangaModel(
    val id: String,
    val title: String,
    val altTitles: List<String>,
    val headerForImage: String,
    val image: String,
)

data class MangaDetailModel(
    val id: String,
    val title: String,
    val altTitles: List<String>,
    val genres: List<String>,
    val headerForImage: String,
    val image: String,
    val chapters: List<ChapterModel>,
)
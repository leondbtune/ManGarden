package com.example.mangarden.test.fakers

import com.example.mangarden.data.database.MangaEntity
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaModel
import com.example.mangarden.model.MangaResultModel
import com.example.mangarden.model.descriptionLangs

object FakeDataSource {
    val searchResult = MangaResultModel(
        currentPage = 1,
        hasNextPage = true,
        results = listOf(
            MangaModel(
                id = "1",
                title = "Manga 1",
                description = "Description 1",
                status = "Status 1",
                releaseDate = 2021,
                contentRating = "Content Rating 1",
                lastVolume = "Last Volume 1",
                lastChapter = "Last Chapter 1",
            ),
            MangaModel(
                id = "2",
                title = "Manga 2",
                description = "Description 2",
                status = "Status 2",
                releaseDate = 2021,
                contentRating = "Content Rating 2",
                lastVolume = "Last Volume 2",
                lastChapter = "Last Chapter 2",
            ),
            MangaModel(
                id = "3",
                title = "Manga 3",
                description = "Description 3",
                status = "Status 3",
                releaseDate = 2021,
                contentRating = "Content Rating 3",
                lastVolume = "Last Volume 3",
                lastChapter = "Last Chapter 3",
            ),
        )
    )
    val databaseMangaList = listOf(
        MangaDetailModel(
            id = "1",
            title = "Manga 1",
            description = descriptionLangs("Description 1"),
            genres = listOf("Genre 1"),
            status = "Status 1",
            releaseDate = 2021,
            chapters = listOf(),
        ),
    )

    val databaseEntries = listOf(
        MangaEntity(
            id = "1",
            chaptersRead = 0,
        ),
        MangaEntity(
            id = "2",
            chaptersRead = 0,
        ),
        MangaEntity(
            id = "3",
            chaptersRead = 0,
        ),
    )

}

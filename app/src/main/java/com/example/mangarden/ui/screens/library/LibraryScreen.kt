package com.example.mangarden.ui.screens.library

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaModel
import com.example.mangarden.ui.screens.shared.MangaCard

/**
 * LibraryScreen is one of the main screens of the app.
 */
@Composable
fun LibraryScreen(
    libraryVM: LibraryVM,
    navigateUp: () -> Unit = {},
    onMangaClicked: () -> Unit,
    modifier: Modifier
) {
    val libraryUiState by libraryVM.libraryUiState.collectAsState()

    /**
     * show different screen based on the state
     */
    when (libraryUiState) {
        is LibraryUiState.Idle -> {
            StartScreen()
        }
        is LibraryUiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is LibraryUiState.Success -> {
            val mangaList = (libraryUiState as LibraryUiState.Success).data
            LibraryScreenGrid(mangaList = mangaList, onMangaClicked = onMangaClicked, libraryVM = libraryVM, modifier = modifier)
        }
        is LibraryUiState.Error -> {
            val error = (libraryUiState as LibraryUiState.Error).error
            ErrorScreen(error = error, modifier = modifier)
        }
    }
}

/**
 * LibraryScreenGrid is the grid of manga cards
 */
@Composable
fun LibraryScreenGrid(
    mangaList: List<MangaDetailModel>,
    onMangaClicked: () -> Unit,
    libraryVM: LibraryVM,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier,
        contentPadding = PaddingValues(2.dp)
    ) {

        items(
            items = mangaDetailModelToMangaModel(mangaList),
            key = { manga -> manga.id }) { manga ->
            MangaCard(manga = manga,
                onMangaClicked = {
                    libraryVM.onMangaClicked(manga)
                    libraryVM.getMangaDetail()
                    onMangaClicked()
                }
            )
        }
    }
}

/**
 * empty start screen
 */
@Composable
fun StartScreen() {

}

/**
 * loading screen
 */
@Composable
fun LoadingScreen(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .padding(100.dp)
    )
}

/**
 * error screen
 */
@Composable
fun ErrorScreen(error: Throwable, modifier: Modifier) {
    Text(text = error.message ?: "Error", modifier = modifier)
}

/**
 * convert list of mangaDetailModel to list of mangaModel
 */
fun mangaDetailModelToMangaModel(mutableList: List<MangaDetailModel>) : List<MangaModel> {
    val resultList = mutableList.map {
        MangaModel(
            id = it.id,
            title = it.title,
            description = it.description.en,
            status = it.status,
            releaseDate = it.releaseDate,
        )
    }
    return resultList
}
package com.example.mangarden.ui.screens.mangaDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mangarden.R
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.ui.screens.search.MangaDetailUiState
import com.example.mangarden.ui.screens.search.SearchVM

/**
 * MangaDetailSearchScreen is the screen for the manga detail screen in the search
 */
@Composable
fun MangaDetailSearchScreen(
    navigateUp: () -> Unit = {},
    searchVM: SearchVM,
    modifier: Modifier = Modifier.testTag("detail_screen_search")
) {

    val mangaDetailUiState by searchVM.mangaDetailUiState.collectAsState()

    /**
     * show different screen based on the state
     */
    when (mangaDetailUiState) {
        is MangaDetailUiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is MangaDetailUiState.Success -> {
            val mangaDetail = (mangaDetailUiState as MangaDetailUiState.Success).data
            DetailScreen(navigateUp = navigateUp, mangaDetail = mangaDetail, searchVM, modifier = modifier)
        }
        is MangaDetailUiState.Error -> {
            val error = (mangaDetailUiState as MangaDetailUiState.Error).error
            ErrorScreen(error = error, modifier = modifier)
        }
    }
}

/**
 * DetailScreen is the screen for the manga detail screen in the search
 */
@Composable
fun DetailScreen(navigateUp: () -> Unit = {}, mangaDetail: MangaDetailModel, searchVM: SearchVM, modifier: Modifier) {
    Column(modifier = modifier.verticalScroll(
        rememberScrollState()
    ).testTag("detailScreenSearch")
    ) {
        Button(onClick = {
            searchVM.addToLibrary()
            navigateUp()
        }
        ) {
            Text(text = stringResource(id = R.string.add_to_library))

        }
        Text(
            text = mangaDetail.title,
            style = MaterialTheme.typography.headlineLarge,
            )
        Text(
            text = stringResource(id = R.string.release_date) + mangaDetail.releaseDate,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = stringResource(id = R.string.description) + mangaDetail.description.en,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = stringResource(id = R.string.genres) + mangaDetail.genres.joinToString(", "),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )

    }
}

/**
 * LoadingScreen is the loading screen
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .padding(100.dp)
    )
}

/**
 * ErrorScreen is the error screen
 */
@Composable
fun ErrorScreen(error: Throwable , modifier: Modifier = Modifier, onRetry: () -> Unit = {}) {
    Text(error.toString(),
        modifier = modifier
            .padding(100.dp)
    )

    Button(onClick = { onRetry }) {

    }

}



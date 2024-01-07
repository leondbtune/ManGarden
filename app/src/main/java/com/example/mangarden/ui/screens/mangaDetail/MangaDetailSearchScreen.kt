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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mangarden.R
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.ui.screens.search.MangaDetailUiState
import com.example.mangarden.ui.screens.search.SearchVM

@Composable
fun MangaDetailSearchScreen(
    searchVM: SearchVM,
    modifier: Modifier
) {

    val mangaDetailUiState by searchVM.mangaDetailUiState.collectAsState()

    when (mangaDetailUiState) {
        is MangaDetailUiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is MangaDetailUiState.Success -> {
            val mangaDetail = (mangaDetailUiState as MangaDetailUiState.Success).data
            DetailScreen(mangaDetail = mangaDetail, searchVM, modifier = modifier)
        }
        is MangaDetailUiState.Error -> {
            val error = (mangaDetailUiState as MangaDetailUiState.Error).error
            ErrorScreen(error = error, modifier = modifier)
        }
    }
}

@Composable
fun DetailScreen(mangaDetail: MangaDetailModel, searchVM: SearchVM, modifier: Modifier) {
    Column(modifier = modifier.verticalScroll(
        rememberScrollState()
    )) {
        Button(onClick = {
            searchVM.addToLibrary()
        }) {
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


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .padding(100.dp)
    )
}

@Composable
fun ErrorScreen(error: Throwable, onRetry: () -> Unit = {}, modifier: Modifier = Modifier) {
    Text(error.toString(),
        modifier = modifier
            .padding(100.dp)
    )

    Button(onClick = { onRetry }) {
        
    }

}



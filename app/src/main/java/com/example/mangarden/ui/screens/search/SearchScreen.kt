package com.example.mangarden.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mangarden.R
import com.example.mangarden.model.MangaModel
import com.example.mangarden.ui.screens.shared.MangaCard
import com.example.mangarden.ui.theme.ManGardenTheme

@ExperimentalMaterial3Api
@Composable
fun SearchScreen(
     modifier: Modifier = Modifier, searchVM: SearchVM = viewModel(factory = SearchVM.Factory), onMangaClicked: () -> Unit = { }
) {
    Column(modifier = modifier) {
        SearchBar(
            searchVM = searchVM,
        )
        val searchUiState by searchVM.searchUiState.collectAsState()
        when (searchUiState) {
            is SearchUiState.Idle -> StartScreen()
            is SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Success -> SearchScreenGrid(mangaList = (searchUiState as SearchUiState.Success).data.results, searchVM = searchVM, onMangaClicked = onMangaClicked )
            is SearchUiState.Error -> ErrorScreen(error = (searchUiState as SearchUiState.Error).error, onRetry = {  } )
        }
    }


}

@Composable
fun SearchScreenGrid(
    mangaList: List<MangaModel>,
    searchVM: SearchVM,
    modifier: Modifier = Modifier,
    onMangaClicked: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier,
        contentPadding = PaddingValues(2.dp)
    ) {

        items(items = mangaList, key = { manga -> manga.id}) { manga ->
            MangaCard(manga = manga,
                onMangaClicked = {
                    searchVM.onMangaClicked(manga)
                    searchVM.getMangaDetail()
                    onMangaClicked()
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, searchVM: SearchVM) {
    TextField(
        value = searchVM.query,
        onValueChange = {
            searchVM.onQueryChange(it)
        },

        label = { Text(stringResource(id = R.string.search_bar)) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        trailingIcon = {
            if (searchVM.query.isNotEmpty()) {
                IconButton(

                    onClick = {
                    searchVM.search()
                    searchVM.onQueryChange("")
                },
                    modifier = Modifier
                        .testTag("search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_button),
                    )

                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .padding(100.dp)
    )
}

@Composable
fun StartScreen(modifier: Modifier = Modifier) {
    Text(stringResource(id = R.string.search_something))
}

@Composable
fun ErrorScreen(error: Throwable, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Text(error.toString())
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    ManGardenTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ManGardenTheme {
        ErrorScreen(error = Throwable("Error"), onRetry = {  } )
    }
}

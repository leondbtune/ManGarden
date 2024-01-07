package com.example.mangarden.ui.screens.mangaDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mangarden.R
import com.example.mangarden.model.ChapterModel
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.ui.screens.library.LibraryVM
import com.example.mangarden.ui.screens.library.MangaDetailLibraryUiState

/**
 * MangaDetailLibraryScreen is the screen for the manga detail screen in the library
 */
@Composable
fun MangaDetailLibraryScreen(
    navigateUp: () -> Unit = {},
    libraryVM: LibraryVM,
    modifier: Modifier
) {
    val mangaDetailLibraryUiState by libraryVM.mangaDetailLibraryUiState.collectAsState()

    /**
     * show different screen based on the state
     */
    when (mangaDetailLibraryUiState) {
        is MangaDetailLibraryUiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is MangaDetailLibraryUiState.Success -> {
            val mangaDetail = (mangaDetailLibraryUiState as MangaDetailLibraryUiState.Success).data
            LibraryDetailScreen(navigateUp = navigateUp, mangaDetail = mangaDetail, libraryVM, modifier = modifier)
        }
        is MangaDetailLibraryUiState.Error -> {
            val error = (mangaDetailLibraryUiState as MangaDetailLibraryUiState.Error).error
            ErrorScreen(error = error, modifier = modifier)
        }
    }
}

/**
 * LibraryDetailScreen is the screen for the manga detail screen in the library
 */
@Composable
fun LibraryDetailScreen(
    navigateUp: () -> Unit = {},
    mangaDetail: MangaDetailModel,
    libraryVM: LibraryVM,
    modifier: Modifier
) {
    Column(modifier = modifier){
        Row {
            Button(onClick = {
                libraryVM.removeFromLibrary(mangaDetail.id)
                navigateUp()
            }) {
                Text(text = stringResource(id = R.string.remove_from_library))

            }
            Button(onClick = {
                libraryVM.reverseChapterList()
            }) {
                Text(text = stringResource(id = R.string.reverse_chapter_order)
             )
            }
        }
        Text(
            text = mangaDetail.title,
            style = MaterialTheme.typography.headlineLarge,
            )
        LazyColumn {
            items(items = mangaDetail.chapters, key = { chapter -> chapter.id }) { chapter ->
                ChapterCard(
                    chapterModel = chapter,
                    modifier = Modifier.padding(2.dp),
                    onReadClick = {
                        libraryVM.addChapterRead(chapter.chapterNumber.toInt())
                    },
                    onUnreadClick = {
                        libraryVM.addChapterRead(chapter.chapterNumber.toInt() - 1)
                    },
                    isRead = chapter.chapterNumber.toInt() <= libraryVM.chaptersRead)
            }
        }
    }

}

/**
 * ChapterCard is the card for the chapter
 */
@Composable
fun ChapterCard(chapterModel: ChapterModel, isRead: Boolean, onReadClick: () -> Unit, onUnreadClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = chapterModel.chapterNumber,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = chapterModel.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )

            if (isRead) {
                IconButton(onClick = {
                    onUnreadClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            } else {
                IconButton(onClick = {
                    onReadClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
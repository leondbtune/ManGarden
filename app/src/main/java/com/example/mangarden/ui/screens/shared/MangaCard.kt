package com.example.mangarden.ui.screens.shared

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mangarden.R
import com.example.mangarden.model.MangaModel

/**
 * MangaCard is the card for the manga
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaCard(
    manga: MangaModel,
    onMangaClicked: () -> Unit,
    modifier: Modifier = Modifier, ) {
    Card(
        modifier = modifier.testTag(manga.title),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            onMangaClicked()
        }


    ) {
        Text(
            text = manga.title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (manga.releaseDate.toString() == "null") stringResource(id = R.string.unknown_release_date) else manga.releaseDate.toString(),
            style = MaterialTheme.typography.bodyLarge)
        Text(
            text = manga.description ?: stringResource(id = R.string.unknown_description),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
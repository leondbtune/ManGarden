package com.example.mangarden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.mangarden.R
import com.example.mangarden.ui.Screen


/**
 * Bottom bar for navigation
 */
@Composable
fun BottomBar(onNavigate: (String) -> Unit, currentPage: Screen) {
    /**
     * Get current item from navigation bar
     */
    val currentItem =  currentPage.name

    /**
     * Navigation bar
     */
    NavigationBar {
        NavigationBarItem(
            selected = currentItem == Screen.Search.name,
            onClick = { onNavigate(Screen.Search.name) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_bottom_bar)
                )
            },
            label = { Text(stringResource(id = R.string.search_bottom_bar)) },
            modifier = Modifier.testTag("search_bottom_bar")
        )
        NavigationBarItem(
            selected = currentItem == Screen.Library.name,
            onClick = { onNavigate(Screen.Library.name) },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = stringResource(id = R.string.library_bottom_bar)
                )
            },
            label = { Text(stringResource(id = R.string.library_bottom_bar)) },
            modifier = Modifier.testTag("library_bottom_bar")
        )
    }
}
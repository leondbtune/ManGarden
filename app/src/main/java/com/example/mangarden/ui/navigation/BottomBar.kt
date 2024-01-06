package com.example.mangarden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mangarden.R
import com.example.mangarden.ui.Screen


@Composable
fun BottomBar(onNavigate: (String) -> Unit, currentPage: Screen) {
    val currentItem =  currentPage.name

    NavigationBar {
        NavigationBarItem(
            selected = currentItem == Screen.Search.name,
            onClick = { onNavigate(Screen.Search.name) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            label = { Text(stringResource(id = R.string.search)) }
        )
        NavigationBarItem(
            selected = currentItem == Screen.Library.name,
            onClick = { onNavigate(Screen.Library.name) },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Library"
                )
            },
            label = { Text(stringResource(id = R.string.library)) }
        )
    }
}
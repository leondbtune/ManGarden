package com.example.mangarden.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangarden.R
import com.example.mangarden.ui.navigation.BottomBar
import com.example.mangarden.ui.screens.mangaDetail.MangaDetailSearchScreen
import com.example.mangarden.ui.screens.search.SearchScreen
import com.example.mangarden.ui.screens.search.SearchUiState
import com.example.mangarden.ui.screens.search.SearchVM

enum class Screen(@StringRes val title: Int) {
    Search(R.string.search),
    Library(R.string.library),
    MangaSearchDetail(R.string.manga_detail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManGardenApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Search.name
    )
    val searchVM: SearchVM = viewModel(factory = SearchVM.Factory)

    Scaffold(
        modifier = Modifier,
        topBar = {
            ManGardenTopBar(
                title = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomBar(
                onNavigate = { route -> navController.navigate(route) },
                currentPage = currentScreen
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Search.name,
        ) {
            composable(route = Screen.Search.name) {
                SearchScreen(
                    searchVM = searchVM,
                    onMangaClicked = {
                        navController.navigate(Screen.MangaSearchDetail.name)
                    },
                    modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
            }

            composable(route = Screen.Library.name) {
                Text(text = "Library")
            }

            composable(route = Screen.MangaSearchDetail.name) {
                MangaDetailSearchScreen(
                    searchVM = searchVM,
                    modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                )
            }

        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManGardenTopBar(
    @StringRes title: Int,
    canNavigateBack: Boolean = false,
    navigateUp: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        title = { Text(stringResource(title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )

                }
            }
        },
    )
}
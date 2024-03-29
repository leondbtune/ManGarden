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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangarden.R
import com.example.mangarden.ui.navigation.BottomBar
import com.example.mangarden.ui.screens.library.LibraryScreen
import com.example.mangarden.ui.screens.library.LibraryVM
import com.example.mangarden.ui.screens.mangaDetail.MangaDetailLibraryScreen
import com.example.mangarden.ui.screens.mangaDetail.MangaDetailSearchScreen
import com.example.mangarden.ui.screens.search.SearchScreen
import com.example.mangarden.ui.screens.search.SearchVM

/**
 * Screen is an enum class that represents the different screens of the app.
 */
enum class Screen(@StringRes val title: Int) {
    Search(R.string.search),
    Library(R.string.library),
    MangaSearchDetail(R.string.manga_detail),
    MangaLibraryDetail(R.string.manga_detail)
}

/**
 * ManGardenApp is the main entry point of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManGardenApp(
    navController: NavHostController = rememberNavController(),
    searchVM: SearchVM = viewModel(factory = SearchVM.Factory),
    libraryVM: LibraryVM = viewModel(factory = LibraryVM.Factory),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Search.name
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            ManGardenTopBar(
                title = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                currentScreen = currentScreen,
            )
        },
        bottomBar = {
            BottomBar(
                onNavigate = { route -> navController.navigate(route) },
                currentPage = currentScreen
            )
        }
    ) { innerPadding ->

        /**
         * NavHost is the navigation component of the app.
         */
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
                libraryVM.getMangaListFromDatabase()
                LibraryScreen(
                    libraryVM = libraryVM,
                    onMangaClicked = {
                        navController.navigate(Screen.MangaLibraryDetail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding))
            }

            composable(route = Screen.MangaSearchDetail.name) {
                MangaDetailSearchScreen(
                    navigateUp = { navController.navigateUp() },
                    searchVM = searchVM,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .testTag("detail_screen_search")
                )
            }

            composable(route = Screen.MangaLibraryDetail.name) {
                MangaDetailLibraryScreen(
                    navigateUp = { navController.navigateUp() },
                    libraryVM = libraryVM,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .testTag("detail_screen_library"),
                )
            }

        }

    }


}

/**
 * ManGardenTopBar is the top bar of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManGardenTopBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false,
    navigateUp: (() -> Unit),
    currentScreen: Screen,

) {
    CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        title = { Text(stringResource(title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack && currentScreen != Screen.Search && currentScreen != Screen.Library)
                IconButton(onClick = navigateUp,
                    modifier = Modifier.testTag("back_button")) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )

                }
            }
        )

    }
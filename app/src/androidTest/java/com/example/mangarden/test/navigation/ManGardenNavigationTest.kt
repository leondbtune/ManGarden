package com.example.mangarden.test.navigation


import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mangarden.R
import com.example.mangarden.test.fakers.FakeRepository
import com.example.mangarden.ui.ManGardenApp
import com.example.mangarden.ui.Screen
import com.example.mangarden.ui.screens.library.LibraryVM
import com.example.mangarden.ui.screens.search.SearchVM
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManGardenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var searchVM: SearchVM
    private lateinit var libraryVM: LibraryVM

    @Before
    fun setupNavHostController() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            searchVM = SearchVM(FakeRepository())
            libraryVM = LibraryVM(FakeRepository())
            ManGardenApp(
                navController = navController,
                searchVM = searchVM,
                libraryVM = libraryVM
            )
        }
    }

    @Test
    fun manGardenNavHost_verifyStartDestination() {
        navController.assertCurrentDestination(Screen.Search.name)
    }

    @Test
    fun manGardenNavHost_verifyBackNavigationNotShownOnSearchScreen() {
        val backText = composeTestRule.activity.getString(R.string.navigate_up)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun manGardenNavHost_navToLibraryScreen() {
        navigateToLibraryScreen()
        navController.assertCurrentDestination(Screen.Library.name)
    }

    @Test
    fun manGardenNavHost_verifyBackNavigationNotShownOnLibraryScreen() {
        navigateToLibraryScreen()
        val backText = composeTestRule.activity.getString(R.string.navigate_up)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun manGardenNavHost_navToSearchScreen() {
        navigateToLibraryScreen()
        navigateToSearchScreen()
        navController.assertCurrentDestination(Screen.Search.name)
    }

    @Test
    fun manGardenNavHost_navToMangaSearchDetailScreen() {
        navigateToMangaSearchDetailScreen()
        navController.assertCurrentDestination(Screen.MangaSearchDetail.name)
    }

    @Test
    fun manGardenNavHost_navToMangaSearchDetailScreenAndBack() {
        navigateToMangaSearchDetailScreen()
        composeTestRule.onNodeWithTag("back_button").performClick()
        navController.assertCurrentDestination(Screen.Search.name)
    }

    @Test
    fun manGardenNavHost_navToMangaLibraryDetailScreen() {
        navigateToLibraryScreen()
        composeTestRule.onNodeWithTag("Manga 1").performClick()
        navController.assertCurrentDestination(Screen.MangaLibraryDetail.name)
    }

    @Test
    fun manGardenNavHost_navToMangaLibraryDetailScreenAndBack() {
        navigateToLibraryScreen()
        composeTestRule.onNodeWithTag("Manga 1").performClick()
        composeTestRule.onNodeWithTag("back_button").performClick()
        navController.assertCurrentDestination(Screen.Library.name)
    }


    private fun navigateToLibraryScreen() {
        composeTestRule.onNodeWithTag("library_bottom_bar").performClick()
    }

    private fun navigateToSearchScreen() {
        composeTestRule.onNodeWithTag("search_bottom_bar").performClick()
    }

    private fun navigateToMangaSearchDetailScreen() {
        navigateToSearchScreen()
        composeTestRule.onNodeWithStringId(R.string.search_bar).performTextInput("Naruto")
        composeTestRule.onNodeWithTag("search_button").performClick()
        composeTestRule.onNodeWithTag("Manga 1").performClick()
    }

}




package com.example.mangarden.test.uiTests

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mangarden.test.fakers.FakeRepository
import com.example.mangarden.test.navigation.onNodeWithStringId
import com.example.mangarden.ui.screens.search.SearchScreen
import com.example.mangarden.ui.screens.search.SearchVM
import com.example.mangarden.ui.theme.ManGardenTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.mangarden.R
import com.example.mangarden.ui.ManGardenApp
import com.example.mangarden.ui.screens.library.LibraryVM
import com.example.mangarden.ui.screens.search.SearchUiState
import kotlinx.coroutines.runBlocking

class ManGardenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var libraryVM: LibraryVM
    lateinit var searchVM: SearchVM
    lateinit var navController: TestNavHostController

    @Before
    fun SearchScreenSetup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            searchVM = SearchVM(FakeRepository())
            libraryVM = LibraryVM(FakeRepository())
            ManGardenTheme {
                ManGardenApp(
                    searchVM = searchVM,
                    libraryVM = libraryVM,
                    navController = navController
                )
            }
        }
    }
    @Test
    fun searchScreenTestSearchBarExists() {
        composeTestRule.onNodeWithText("search bar").assertExists()
    }

    @Test
    fun searchScreenTestSearchBarText() {
        composeTestRule.onNodeWithText("search bar").performTextInput("test")
        composeTestRule.onNodeWithTag("search_button").performClick()
        composeTestRule.onNodeWithTag("Manga 1").assertExists()
    }

    @Test
    fun libraryScreenTestLibraryScreenExists() {
        composeTestRule.onNodeWithText("library").performClick()
        composeTestRule.onAllNodesWithText("library").assertCountEquals(2)
    }

    @Test
    fun emptyLibraryScreenTestEmptyLibraryScreenExists() {
        composeTestRule.onNodeWithText("library").performClick()
        composeTestRule.onNodeWithTag("Manga 2").assertDoesNotExist()
    }

    @Test
    fun libraryScreenTestLibraryScreenText() {
        composeTestRule.onNodeWithText("library").performClick()
        composeTestRule.onNodeWithTag("Manga 1").assertExists()
    }

    @Test
    fun libraryScreenDetail() {
        composeTestRule.onNodeWithText("library").performClick()
        composeTestRule.onNodeWithTag("Manga 1").performClick()
        composeTestRule.onNodeWithTag("detail_screen_library").assertExists()
    }

}
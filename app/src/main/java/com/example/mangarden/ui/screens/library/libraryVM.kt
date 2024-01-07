package com.example.mangarden.ui.screens.library

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mangarden.ManGardenApplication
import com.example.mangarden.data.MangaRepository
import com.example.mangarden.model.MangaDetailModel
import com.example.mangarden.model.MangaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UiState for the library screen
 */
sealed interface LibraryUiState {
    object Idle : LibraryUiState
    object Loading : LibraryUiState
    data class Success(val data: List<MangaDetailModel>) : LibraryUiState
    data class Error(val error: Throwable) : LibraryUiState
}


/**
 * UiState for the manga detail screen
 */
sealed interface MangaDetailLibraryUiState {
    object Loading : MangaDetailLibraryUiState
    data class Success(val data: MangaDetailModel) : MangaDetailLibraryUiState
    data class Error(val error: Throwable) : MangaDetailLibraryUiState
}

/**
 * LibraryVM is the ViewModel for the library screen and manga detail screen
 */
class LibraryVM(private val repository: MangaRepository): ViewModel() {
    private val _libraryUiState = MutableStateFlow<LibraryUiState>(LibraryUiState.Idle)
    val libraryUiState: MutableStateFlow<LibraryUiState> = _libraryUiState

    /**
     * getMangaListFromDatabase gets the manga list from the database
     */
    fun getMangaListFromDatabase() {
        viewModelScope.launch {
            _libraryUiState.value = LibraryUiState.Loading
            _libraryUiState.value = try {
                LibraryUiState.Success(repository.getMangaListFromDatabase())
            } catch (e: Throwable) {
                LibraryUiState.Error(e)
            }
        }
    }

    private val _mangaDetailLibraryUiState = MutableStateFlow<MangaDetailLibraryUiState>(MangaDetailLibraryUiState.Loading)
    val mangaDetailLibraryUiState: StateFlow<MangaDetailLibraryUiState> = _mangaDetailLibraryUiState.asStateFlow()

    /**
     * highLightedMangaId is the id of the manga that is highlighted, used for the detail screen
     */
    private var highLightedMangaId by mutableStateOf("")

    /**
     * reverseChapterList reverses the chapter list
     */
    fun reverseChapterList() {
        val mangaDetail = (_mangaDetailLibraryUiState.value as MangaDetailLibraryUiState.Success).data
        val reversedChapterList = mangaDetail.chapters.reversed()
        val reversedMangaDetail = MangaDetailModel(
            id = mangaDetail.id,
            title = mangaDetail.title,
            description = mangaDetail.description,
            genres = mangaDetail.genres,
            status = mangaDetail.status,
            releaseDate = mangaDetail.releaseDate,
            chapters = reversedChapterList
        )
        _mangaDetailLibraryUiState.value = MangaDetailLibraryUiState.Success(reversedMangaDetail)
    }

    /**
     * onMangaClicked is called when a mangaCard is clicked
     */
    fun onMangaClicked(manga: MangaModel) {
        highLightedMangaId = manga.id
        updateChaptersRead()
    }

    /**
     * getMangaDetail gets the manga detail from the database
     */
    fun getMangaDetail() {
        viewModelScope.launch {
            _mangaDetailLibraryUiState.value = MangaDetailLibraryUiState.Loading
            _mangaDetailLibraryUiState.value = try {
                Log.d("MangaDetailLibraryUiState", "Loading manga detail for $highLightedMangaId")
                MangaDetailLibraryUiState.Success(repository.getMangaDetail(highLightedMangaId))
            } catch (e: Throwable) {
                MangaDetailLibraryUiState.Error(e)
            }
        }
    }

    var chaptersRead by mutableIntStateOf(0)

    /**
     * repository call to delete manga from library
     */
    fun removeFromLibrary(id: String) {
        viewModelScope.launch {
            repository.deleteManga(id)
        }
    }

    /**
     * updateChaptersRead updates the chapters read in the database
     */
    private fun updateChaptersRead() {
        viewModelScope.launch { repository.updateManga(highLightedMangaId, chaptersRead)  }
    }

    /**
     * addChapterRead adds a chapter read to the database
     */
    fun addChapterRead(chapterRead: Int) {
            chaptersRead = chapterRead
            viewModelScope.launch {
                repository.updateManga(highLightedMangaId, chaptersRead)
            }
    }

    /**
     * factory for the libraryVM
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ManGardenApplication)
                val repository = application.container.mangaRepository
                LibraryVM(repository)
            }
        }
    }
}
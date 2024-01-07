package com.example.mangarden.ui.screens.search

import androidx.compose.runtime.getValue
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
import com.example.mangarden.model.MangaResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UiState for the search screen
 */
sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val data: MangaResultModel) : SearchUiState
    data class Error(val error: Throwable) : SearchUiState
}

/**
 * UiState for the manga detail screen
 */
sealed interface MangaDetailUiState {
    object Loading : MangaDetailUiState
    data class Success(val data: MangaDetailModel) : MangaDetailUiState
    data class Error(val error: Throwable) : MangaDetailUiState
}

/**
 * SearchVM is the ViewModel for the search screen and manga detail screen
 */
class SearchVM(private val repository: MangaRepository) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    var query by mutableStateOf("")

    /**
     * onQueryChange is called when the query is changed in the search bar
     */
    fun onQueryChange(query: String) {
        this.query = query
    }

    /**
     * search searches for manga based on the query
     */
    fun search() {
        viewModelScope.launch {
            _searchUiState.value = SearchUiState.Loading
            _searchUiState.value = try {
                SearchUiState.Success(repository.getMangaList(query))
            } catch (e: Throwable) {
                SearchUiState.Error(e)
            }
        }
    }

    private val _mangaDetailUiState = MutableStateFlow<MangaDetailUiState>(MangaDetailUiState.Loading)
    val mangaDetailUiState: StateFlow<MangaDetailUiState> = _mangaDetailUiState.asStateFlow()

    private var highLightedMangaId by mutableStateOf("")

    /**
     * onMangaClicked is called when a manga is clicked
     */
    fun onMangaClicked(manga: MangaModel) {
        highLightedMangaId = manga.id
    }

    /**
     * getMangaDetail gets the manga detail
     */
    fun getMangaDetail() {
        viewModelScope.launch {
            _mangaDetailUiState.value = MangaDetailUiState.Loading
            _mangaDetailUiState.value = try {
                MangaDetailUiState.Success(repository.getMangaDetail(highLightedMangaId))
            } catch (e: Throwable) {
                MangaDetailUiState.Error(e)
            }
        }
    }

    //

    /**
     * addToLibrary adds the manga to the database
     */
    fun addToLibrary() {
        viewModelScope.launch {
            repository.insertManga(highLightedMangaId)
        }
    }

    /**
     * factory for the ViewModel
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ManGardenApplication)
                val repository = application.container.mangaRepository
                SearchVM(repository = repository)
            }
        }
    }
}
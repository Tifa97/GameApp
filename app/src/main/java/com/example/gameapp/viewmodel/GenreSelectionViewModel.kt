package com.example.gameapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.db.entity.GenreItem
import com.example.gameapp.model.response.Genre
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.repository.DataStorePreferences
import com.example.gameapp.repository.DatabaseRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GenreSelectionViewModel(
    private val dataStore: DataStorePreferences,
    private val backendRepository: BackendRepository,
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    val savedGenres = databaseRepository.genresFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val isOnboardingDone = dataStore.isOnboardingDoneFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _genres = mutableStateOf<List<Genre>>(listOf())
    val genres: State<List<Genre>> = _genres

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadError = mutableStateOf("")
    val loadError: State<String> = _loadError

    private var editedGenres = mutableListOf<GenreItem>()

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = backendRepository.getGenres()
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _genres.value = it.results
                    }
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    result.message?.let {
                        _loadError.value = it
                    }
                    _isLoading.value = false
                }
                else -> {}
            }
        }
    }

    fun saveGenreEdits() {
        viewModelScope.launch {
            editedGenres.forEach { genre ->
                when {
                    genre.isSelected && !savedGenres.value.any { it.genreId == genre.genreId } -> {
                        databaseRepository.upsertGenre(genre)
                    }
                    !genre.isSelected && savedGenres.value.any { it.genreId == genre.genreId } -> {
                        databaseRepository.deleteGenreByGenreId(genre.genreId)
                    }
                    else -> {}
                }
            }
        }
    }

    fun editSelectedGenreList(genre: GenreItem) {
        if (!editedGenres.any { it.genreId == genre.genreId }) {
            editedGenres.add(genre)
        } else {
            editedGenres.removeIf { it.genreId == genre.genreId }
            editedGenres.add(genre)
        }
    }

    fun saveOnboardingFinished() {
        viewModelScope.launch {
            dataStore.saveIsOnboardingDone(true)
        }
    }
}
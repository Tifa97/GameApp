package com.example.gameapp.viewmodel

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

    var genres = mutableStateOf<List<Genre>>(listOf())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")

    private var editedGenres = mutableListOf<GenreItem>()

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            isLoading.value = true
            val result = backendRepository.getGenres()
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        genres.value = it.results
                    }
                    isLoading.value = false
                }
                is Resource.Error -> {
                    result.message?.let {
                        loadError.value = it
                    }
                    isLoading.value = false
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
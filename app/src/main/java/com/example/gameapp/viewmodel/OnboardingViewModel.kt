package com.example.gameapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.db.entity.GenreItem
import com.example.gameapp.model.response.Genre
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.repository.DataStorePreferences
import com.example.gameapp.repository.DatabaseRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dataStore: DataStorePreferences,
    private val backendRepository: BackendRepository,
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    var genres = mutableStateOf<List<Genre>>(listOf())
    var selectedGenres = mutableStateOf<MutableList<GenreItem>>(mutableListOf())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var isOnboardingFinished = mutableStateOf(false)

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

    fun finishOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedGenres.value.forEach {
                Log.d("GameApp", "Saving ${it.name} to db")
                databaseRepository.upsertGenre(it)
            }
            dataStore.saveIsOnboardingDone(true)
            isOnboardingFinished.value = true
        }
    }

    fun editSelectedGenreList(genre: GenreItem) {
        if (genre.isSelected && !selectedGenres.value.contains(genre)) selectedGenres.value.add(genre)
        if (!genre.isSelected && selectedGenres.value.contains(genre)) selectedGenres.value.remove(genre)
    }
}
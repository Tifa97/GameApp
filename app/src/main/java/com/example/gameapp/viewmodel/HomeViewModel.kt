package com.example.gameapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.model.response.Game
import com.example.gameapp.model.response.Genre
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.repository.DatabaseRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val backendRepository: BackendRepository,
    databaseRepository: DatabaseRepository
): ViewModel() {
    var games = mutableStateOf<List<Game>>(listOf())
    val savedGenres = databaseRepository.genresFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var selectedGenre: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            selectedGenre.collect {
                getGamesForGenre(it)
            }
        }
    }

    fun getGamesForGenre(genreName: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = backendRepository.getGamesForGenre(genreName)
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        games.value = it.results
                        it.results.forEach { game ->
                            game.name?.let { name ->
                                Log.d("TestGames", name)
                            }
                        }
                    }
                    isLoading.value = false
                }
                is Resource.Error -> {
                    result.message?.let {
                        loadError.value = it
                        isLoading.value = false
                    }
                }
                else -> {}
            }
        }
    }

    fun setSelectedGenre(genreName: String) {
        viewModelScope.launch {
            selectedGenre.emit(genreName)
        }
    }
}
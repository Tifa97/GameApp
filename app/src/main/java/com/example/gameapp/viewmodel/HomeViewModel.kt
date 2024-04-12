package com.example.gameapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.remote.response.Game
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.repository.DatabaseRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val backendRepository: BackendRepository,
    databaseRepository: DatabaseRepository
): ViewModel() {
    val savedGenres = databaseRepository.genresFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var selectedGenre: MutableStateFlow<String> = MutableStateFlow("")

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadError = mutableStateOf("")
    val loadError: State<String> = _loadError

    private val _games = mutableStateOf<List<Game>>(listOf())
    val games: State<List<Game>> = _games

    init {
        viewModelScope.launch {
            selectedGenre.collect {
                getGamesForGenre(it)
            }
        }
    }

    private fun getGamesForGenre(genreName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = backendRepository.getGamesForGenre(genreName)
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        _games.value = it.results
                    }
                    if (_loadError.value.isNotEmpty()) _loadError.value = ""
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    result.message?.let {
                        _loadError.value = it
                        _isLoading.value = false
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

    fun setErrorMessage(message: String) {
        _loadError.value = message
    }
}
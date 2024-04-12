package com.example.gameapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.remote.response.GameDetailsResponse
import com.example.gameapp.remote.response.Platform
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.launch

class GameDetailsViewModel(
    private val backendRepository: BackendRepository
): ViewModel() {
    private val _gameDetails = mutableStateOf<GameDetailsResponse?>(null)
    val gameDetails: State<GameDetailsResponse?> = _gameDetails

    private val _platformNames = mutableStateOf("")
    val platformNames: State<String> = _platformNames

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadError = mutableStateOf("")
    val loadError: State<String> = _loadError

    fun getGameDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = backendRepository.getGameDetails(id)
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        _gameDetails.value = it
                        addPlatformNamesToList(it.platforms)
                    }
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

    private fun addPlatformNamesToList(platforms: List<Platform>?) {
        val resultList = mutableListOf<String>()
        platforms?.let { list ->
            list.forEach { platform ->
                platform.platform?.name?.let { resultList.add(it) }
            }
            _platformNames.value = resultList.joinToString(separator = ", ")
        }
    }
}
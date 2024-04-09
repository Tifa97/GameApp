package com.example.gameapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.model.response.GameDetailsResponse
import com.example.gameapp.model.response.Platform
import com.example.gameapp.navigation.Screen
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.util.Resource
import kotlinx.coroutines.launch

class GameDetailsViewModel(
    private val backendRepository: BackendRepository
): ViewModel() {
    var gameDetails = mutableStateOf<GameDetailsResponse?>(null)
    var platformNames = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")

    fun getGameDetails(id: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = backendRepository.getGameDetails(id)
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        gameDetails.value = it
                        addPlatformNamesToList(it.platforms)
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

    private fun addPlatformNamesToList(platforms: List<Platform>?) {
        val resultList = mutableListOf<String>()
        platforms?.let { list ->
            list.forEach { platform ->
                platform.platform?.name?.let { resultList.add(it) }
            }
            platformNames.value = resultList.joinToString(separator = ", ")
        }
    }
}
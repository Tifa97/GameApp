package com.example.gameapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.model.response.Genre
import com.example.gameapp.repository.BackendRepository
import com.example.gameapp.repository.DataStorePreferences
import com.example.gameapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dataStore: DataStorePreferences,
    private val backendRepository: BackendRepository
): ViewModel() {
    var genres = mutableStateOf<List<Genre>>(listOf())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")

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
                    genres.value.forEach {
                        Log.d("GameApp", it.name!!)
                    }
                    isLoading.value = false
                }
                is Resource.Error -> {
                    result.message?.let {
                        loadError.value = it
                    }
                    Log.e("GameApp", loadError.value)
                    isLoading.value = false
                }
                is Resource.Loading -> { isLoading.value = false }
            }
        }
    }

    fun finishOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveIsOnboardingDone(true)
        }
    }
}
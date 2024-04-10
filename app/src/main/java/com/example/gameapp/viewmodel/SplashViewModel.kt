package com.example.gameapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.navigation.Screen
import com.example.gameapp.repository.DataStorePreferences
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dataStore: DataStorePreferences
): ViewModel() {
    private val _startDestination = mutableStateOf(Screen.GenreSelection.route)
    val startDestination: State<String> = _startDestination

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            dataStore.isOnboardingDoneFlow.collect {
                _startDestination.value = if (it) Screen.Home.route else Screen.GenreSelection.route
            }
            _isLoading.value = false
        }
    }
}
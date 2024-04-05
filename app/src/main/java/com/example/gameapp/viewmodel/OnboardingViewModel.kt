package com.example.gameapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.repository.DataStorePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dataStore: DataStorePreferences
): ViewModel() {
    fun finishOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveIsOnboardingDone(true)
        }
    }
}
package com.example.gameapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.repository.DatabaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    val savedGenres = databaseRepository.genresFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
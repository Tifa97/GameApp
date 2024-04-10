package com.example.gameapp.di

import com.example.gameapp.db.GameRoom
import com.example.gameapp.repository.BackendRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.gameapp.viewmodel.SplashViewModel
import com.example.gameapp.repository.DataStorePreferences
import com.example.gameapp.repository.DatabaseRepository
import com.example.gameapp.services.RawgApi
import com.example.gameapp.viewmodel.GameDetailsViewModel
import com.example.gameapp.viewmodel.HomeViewModel
import com.example.gameapp.viewmodel.GenreSelectionViewModel

val gameAppModule = module {
    single { GameRoom.getInstance(get()) }
    single { get<GameRoom>().dao }

    single { DataStorePreferences(get()) }
    single { BackendRepository(get(), get()) }
    single { RawgApi }
    single { DatabaseRepository(get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { GenreSelectionViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { GameDetailsViewModel(get()) }
}
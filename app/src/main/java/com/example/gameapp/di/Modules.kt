package com.example.gameapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.gameapp.viewmodel.SplashViewModel
import com.example.gameapp.repository.DataStorePreferences
import com.example.gameapp.viewmodel.OnboardingViewModel

val gameAppModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { OnboardingViewModel(get()) }

    single { DataStorePreferences(get()) }
}
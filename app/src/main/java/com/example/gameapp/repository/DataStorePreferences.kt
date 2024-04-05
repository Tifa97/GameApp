package com.example.gameapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    private val dataStore = context.dataStore

    suspend fun saveIsOnboardingDone(isOnboardingDone: Boolean) {
        dataStore.edit {
            it[IS_ONBOARDING_DONE] = isOnboardingDone
        }
    }

    val isOnboardingDoneFlow: Flow<Boolean>
        get() = dataStore.data.map {
            it[IS_ONBOARDING_DONE] ?: false
        }

    companion object {
        private const val PREFERENCES_NAME = "game_app_preferences"

        private val IS_ONBOARDING_DONE = booleanPreferencesKey("is_onboarding_done")
    }
}
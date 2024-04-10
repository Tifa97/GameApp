package com.example.gameapp.repository

import android.content.Context
import android.util.Log
import com.example.gameapp.R
import com.example.gameapp.model.response.GameDetailsResponse
import com.example.gameapp.model.response.GamesResponse
import com.example.gameapp.model.response.GenresResponse
import com.example.gameapp.services.RawgApiClient
import com.example.gameapp.util.Resource

class BackendRepository(
    private val rawgApi: RawgApiClient,
    private val context: Context
) {
    suspend fun getGenres(): Resource<GenresResponse> {
        val result = try {
            rawgApi.retrofitService.getGenres()
        } catch (e: Exception) {
            Log.e("GameApp", e.stackTraceToString())
            return Resource.Error(context.getString(R.string.get_genres_error))
        }
        return Resource.Success(result)
    }

    suspend fun getGamesForGenre(genre: String): Resource<GamesResponse> {
        val result = try {
            // API gives correct response only if genre name is in lowercase
            rawgApi.retrofitService.getGamesForGenre(genres = genre.lowercase())
        } catch (e: Exception) {
            Log.e("GameApp", e.stackTraceToString())
            return Resource.Error(context.getString(R.string.get_games_error))
        }
        return Resource.Success(result)
    }

    suspend fun getGameDetails(id: String): Resource<GameDetailsResponse> {
        val result = try {
            // API gives correct response only if genre name is in lowercase
            rawgApi.retrofitService.getGameDetails(id = id)
        } catch (e: Exception) {
            Log.e("GameApp", e.stackTraceToString())
            return Resource.Error(context.getString(R.string.get_game_details_error))
        }
        return Resource.Success(result)
    }
}
package com.example.gameapp.repository

import android.util.Log
import com.example.gameapp.model.response.GamesResponse
import com.example.gameapp.model.response.GenresResponse
import com.example.gameapp.services.RawgApi
import com.example.gameapp.util.Resource

class BackendRepository(private val rawgApi: RawgApi) {
    suspend fun getGenres(): Resource<GenresResponse> {
        val result = try {
            rawgApi.retrofitService.getGenres()
        } catch (e: Exception) {
            Log.e("GameApp", e.stackTraceToString())
            return Resource.Error("Error fetching data from server")
        }
        return Resource.Success(result)
    }

    suspend fun getGamesForGenre(genre: String): Resource<GamesResponse> {
        val result = try {
            // API gives correct response only if genre name is in lowercase
            rawgApi.retrofitService.getGamesForGenre(genres = genre.lowercase())
        } catch (e: Exception) {
            Log.e("GameApp", e.stackTraceToString())
            return Resource.Error("Error fetching data from server")
        }
        return Resource.Success(result)
    }
}
package com.example.gameapp.remote

import com.example.gameapp.BuildConfig
import com.example.gameapp.remote.response.GameDetailsResponse
import com.example.gameapp.remote.response.GamesResponse
import com.example.gameapp.remote.response.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApiInterface {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = BuildConfig.API_KEY
    ): GenresResponse

    @GET("games")
    suspend fun getGamesForGenre(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("genres") genres: String
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GameDetailsResponse
}
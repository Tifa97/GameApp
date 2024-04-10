package com.example.gameapp.services

import com.example.gameapp.model.response.GameDetailsResponse
import com.example.gameapp.model.response.GamesResponse
import com.example.gameapp.model.response.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApiInterface {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = API_KEY
    ): GenresResponse

    @GET("games")
    suspend fun getGamesForGenre(
        @Query("key") key: String = API_KEY,
        @Query("genres") genres: String
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: String,
        @Query("key") key: String = API_KEY
    ): GameDetailsResponse
}
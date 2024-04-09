package com.example.gameapp.services

import com.example.gameapp.model.response.GameDetailsResponse
import com.example.gameapp.model.response.GamesResponse
import com.example.gameapp.model.response.GenresResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.rawg.io/api/"
const val API_KEY = "e78b21663ecc41f58b15af88e902f1b4"

val json = Json { ignoreUnknownKeys = true }

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Set logging level to include body
}

// Create OkHttpClient and add the logging interceptor
val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface RawgApiService {
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

object RawgApi {
    val retrofitService: RawgApiService by lazy {
        retrofit.create(RawgApiService::class.java)
    }
}
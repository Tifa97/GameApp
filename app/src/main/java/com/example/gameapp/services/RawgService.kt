package com.example.gameapp.services

import com.example.gameapp.model.response.GenresResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.rawg.io/api/"
const val API_KEY = "e78b21663ecc41f58b15af88e902f1b4"

val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface RawgApiService {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = API_KEY
    ): GenresResponse
}

object RawgApi {
    val retrofitService: RawgApiService by lazy {
        retrofit.create(RawgApiService::class.java)
    }
}
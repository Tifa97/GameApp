package com.example.gameapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.gameapp.db.entity.GenreItem
import com.example.gameapp.model.response.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Upsert
    suspend fun upsertGenre(genre: GenreItem)

    @Delete
    suspend fun deleteGenre(genre: GenreItem)

    @Query("SELECT * FROM genre")
    fun getGenres(): Flow<List<GenreItem>>
}
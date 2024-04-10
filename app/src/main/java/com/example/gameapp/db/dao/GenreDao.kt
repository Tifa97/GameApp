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

    @Query("DELETE FROM genre WHERE genreId = :genreId")
    suspend fun deleteGenreByGenreId(genreId: Int)

    @Query("SELECT * FROM genre")
    fun getGenres(): Flow<List<GenreItem>>
}
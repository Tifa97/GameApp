package com.example.gameapp.repository

import com.example.gameapp.db.dao.GenreDao
import com.example.gameapp.db.entity.GenreItem
import com.example.gameapp.model.response.Genre
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val genreDao: GenreDao) {
    suspend fun upsertGenre(genre: GenreItem) {
        genreDao.upsertGenre(genre)
    }

    suspend fun deleteGenre(genre: GenreItem) {
        genreDao.deleteGenre(genre)
    }

    val genresFlow: Flow<List<GenreItem>>
        get() = genreDao.getGenres()
}
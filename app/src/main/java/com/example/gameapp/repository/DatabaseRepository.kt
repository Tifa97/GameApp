package com.example.gameapp.repository

import com.example.gameapp.db.dao.GenreDao
import com.example.gameapp.db.entity.GenreItem
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val genreDao: GenreDao) {
    suspend fun upsertGenre(genre: GenreItem) {
        genreDao.upsertGenre(genre)
    }

    suspend fun deleteGenreByGenreId(genreId: Int) {
        genreDao.deleteGenreByGenreId(genreId)
    }

    val genresFlow: Flow<List<GenreItem>>
        get() = genreDao.getGenres()
}
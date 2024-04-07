package com.example.gameapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("genre")
data class GenreItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genreId: Int,
    val name: String,
    var isSelected: Boolean
)
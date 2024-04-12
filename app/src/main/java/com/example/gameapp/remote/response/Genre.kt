package com.example.gameapp.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName(value = "id")
    val id: Int?,
    @SerialName(value = "games_count")
    val games_count: Int?,
    @SerialName(value = "image_background")
    val image_background: String?,
    @SerialName(value = "name")
    val name: String?,
    @SerialName(value = "slug")
    val slug: String?
)

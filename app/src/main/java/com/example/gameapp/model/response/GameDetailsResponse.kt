package com.example.gameapp.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsResponse(
    @SerialName(value = "id")
    val id: Int?,
    @SerialName(value = "name")
    val name: String?,
    @SerialName(value = "released")
    val released: String?,
    @SerialName(value = "added")
    val added: Int?,
    @SerialName(value = "description")
    val description: String?,
    @SerialName(value = "website")
    val website: String?,
    @SerialName(value = "rating")
    val rating: Double?,
    @SerialName(value = "playtime")
    val playtime: Int?,
    @SerialName(value = "background_image")
    val backgroundImage: String?,
    @SerialName(value = "platforms")
    val platforms: List<Platform>?,
    @SerialName(value = "slug")
    val slug: String?,
)
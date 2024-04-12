package com.example.gameapp.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    @SerialName(value = "id")
    val id: Int?,
    @SerialName(value = "background_image")
    val backgroundImage: String?,
    @SerialName(value = "name")
    val name: String?,
    @SerialName(value = "released")
    val released: String?
)
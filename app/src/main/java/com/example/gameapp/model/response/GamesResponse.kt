package com.example.gameapp.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(
    @SerialName(value = "count")
    val count: Int?,
    @SerialName(value = "next")
    val next: String?,
    @SerialName(value = "previous")
    val previous: String?,
    @SerialName(value = "results")
    val results: List<Game>
)
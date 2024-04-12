package com.example.gameapp.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformX(
    @SerialName(value = "id")
    val id: Int?,
    @SerialName(value = "name")
    val name: String?,
    @SerialName(value = "slug")
    val slug: String?
)

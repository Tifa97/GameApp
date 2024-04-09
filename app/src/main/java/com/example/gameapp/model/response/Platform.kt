package com.example.gameapp.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Platform(
    @SerialName(value = "platform")
    val platform: PlatformX?,
)
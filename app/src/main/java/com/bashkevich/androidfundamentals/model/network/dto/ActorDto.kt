package com.bashkevich.androidfundamentals.model.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val cast: List<ActorDto>,
)

@Serializable
data class ActorDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String?
)
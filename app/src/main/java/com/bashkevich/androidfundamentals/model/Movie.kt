package com.bashkevich.androidfundamentals.model

data class Movie(
    val minAge: Int?,
    val posterImageId: Int?,
    val title: String?,
    val genres: String?,
    val rating: Double?,
    val reviews: Int?,
    val duration: Int?
)
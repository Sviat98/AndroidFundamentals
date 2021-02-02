package com.bashkevich.androidfundamentals.model.viewobject

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int?,
    val genres: List<String>?,
    val actors: List<Actor>? = null
)
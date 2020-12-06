package com.bashkevich.androidfundamentals.model

import androidx.annotation.DrawableRes

data class Movie(
    val minAge: Int,
    @DrawableRes
    val posterImageId: Int,
    val title: String,
    val genres: String,
    val rating: Double,
    val reviews: Int,
    val duration: Int
)
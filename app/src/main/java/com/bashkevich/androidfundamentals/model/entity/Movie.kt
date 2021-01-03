package com.bashkevich.androidfundamentals.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val genres: List<Genre>?,
    var actors: List<Actor>
) : Parcelable
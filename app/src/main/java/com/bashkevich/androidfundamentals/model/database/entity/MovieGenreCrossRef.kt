package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "genre_id"])
class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int
)

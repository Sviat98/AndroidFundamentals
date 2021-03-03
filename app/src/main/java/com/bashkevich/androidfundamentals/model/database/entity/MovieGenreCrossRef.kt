package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["movie_id", "genre_id"],foreignKeys = [
    ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["movie_id"],
        childColumns = ["movie_id"],
        onDelete = ForeignKey.CASCADE
    )
])
class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int
)

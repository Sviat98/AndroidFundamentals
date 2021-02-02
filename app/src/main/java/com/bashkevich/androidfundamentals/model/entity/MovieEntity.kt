package com.bashkevich.androidfundamentals.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashkevich.androidfundamentals.model.dto.GenreDto
import kotlinx.serialization.SerialName

@Entity(tableName = "movies")
class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "runtime")
    val runtime: Int?,
    @ColumnInfo(name = "genres")
    val genres: String?,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int,
)
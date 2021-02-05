package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        entity = GenreEntity::class,
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<GenreEntity>
)
package com.bashkevich.androidfundamentals.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class MovieWithActors(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "actor_id",
        entity = ActorEntity::class,
        associateBy = Junction(
            value = MovieActorCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "actor_id"
        )
    )
    val actors: List<ActorEntity>
)
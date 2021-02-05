package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "actor_id"])
class MovieActorCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "actor_id")
    val actorId: Int
)
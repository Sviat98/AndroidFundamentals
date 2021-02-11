package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors")
data class ActorEntity(
   @PrimaryKey
   @ColumnInfo(name = "actor_id")
   val actorId: Int,
   @ColumnInfo(name = "profile_path")
   val profilePath: String?,
   @ColumnInfo(name = "actor_name")
   val actorName: String
)


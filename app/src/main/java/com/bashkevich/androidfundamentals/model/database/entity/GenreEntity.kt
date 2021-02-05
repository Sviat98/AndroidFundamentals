package com.bashkevich.androidfundamentals.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    val genreName: String
)
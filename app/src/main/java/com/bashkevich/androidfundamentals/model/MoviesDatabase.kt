package com.bashkevich.androidfundamentals.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashkevich.androidfundamentals.model.entity.ActorEntity
import com.bashkevich.androidfundamentals.model.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.entity.MovieEntity
import com.bashkevich.androidfundamentals.model.entity.MovieWithActors

@Database(
    entities = [ActorEntity::class, MovieEntity::class, MovieActorCrossRef::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies_db"
            ).build()
    }
}
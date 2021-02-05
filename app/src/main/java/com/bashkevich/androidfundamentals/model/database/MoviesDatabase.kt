package com.bashkevich.androidfundamentals.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashkevich.androidfundamentals.model.database.entity.*

@Database(
    entities = [ActorEntity::class, MovieEntity::class, GenreEntity::class, MovieActorCrossRef::class, MovieGenreCrossRef::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        private const val DB_NAME = "movies_db"

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MoviesDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
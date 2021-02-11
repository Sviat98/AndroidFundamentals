package com.bashkevich.androidfundamentals.model.database

import androidx.room.*
import com.bashkevich.androidfundamentals.model.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies where movie_id=:movieId")
    suspend fun getMovieWithGenresAndActorsById(movieId: Int): MovieWithGenresAndActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActors(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActorCrossRef(join: MovieActorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRef(join: MovieGenreCrossRef)

    @Query("SELECT * FROM movies")
    fun getAllMoviesWithGenresAndUpdate(): Flow<List<MovieWithGenres>>
}
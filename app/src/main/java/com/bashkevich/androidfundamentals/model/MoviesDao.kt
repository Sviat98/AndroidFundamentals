package com.bashkevich.androidfundamentals.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bashkevich.androidfundamentals.model.entity.ActorEntity
import com.bashkevich.androidfundamentals.model.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.entity.MovieEntity
import com.bashkevich.androidfundamentals.model.entity.MovieWithActors

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies where movie_id=:movieId")
    suspend fun getMovieWithActorsById(movieId: Int): MovieWithActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActors(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoin(join: MovieActorCrossRef)

    @Query("SELECT * FROM movies")
    fun getAllMoviesAndUpdate(): LiveData<List<MovieEntity>>

}
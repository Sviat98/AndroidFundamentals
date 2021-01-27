package com.bashkevich.androidfundamentals.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.bashkevich.androidfundamentals.model.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(applicationContext: Context) {
    val database: MoviesDatabase = MoviesDatabase.create(applicationContext)

    fun getAllMovies() = Transformations.map(database.moviesDao.getAllMovies()) { entities ->
        entities.map { EntityMapper.fromMovieEntity(it) }
    }

    suspend fun findMovieById(movieId: Int) = withContext(Dispatchers.IO) {
        EntityMapper.fromMovieWithActors(database.moviesDao.getMovieWithActorsById(movieId))
    }

    suspend fun refreshMovies() = withContext(Dispatchers.IO) {
        val nowPlayingMoviesIds =
            RetrofitModule.moviesApi.getNowPlayingMovies().results.map { it.id }
        val upcomingMoviesIds = RetrofitModule.moviesApi.getUpcomingMovies().results.map { it.id }
        val popularMoviesIds = RetrofitModule.moviesApi.getPopularMovies().results.map { it.id }
        val topRatedMoviesIds = RetrofitModule.moviesApi.getTopRatedMovies().results.map { it.id }

        val allMoviesIds = topRatedMoviesIds.plus(popularMoviesIds).plus(upcomingMoviesIds)
            .plus(nowPlayingMoviesIds).distinct()

        allMoviesIds.forEach { movieId ->

            val movieEntity =
                EntityMapper.toMovieEntity(RetrofitModule.moviesApi.getMovieById(movieId))

            database.moviesDao.insertMovie(movieEntity)

            val actorsEntities =
                RetrofitModule.moviesApi.getActors(movieId).cast.filter { it.profilePicture != null }
                    .map { EntityMapper.toActorEntity(it) }

            database.moviesDao.insertAllActors(actorsEntities)

            val actorsIds = actorsEntities.map { it.actorId }

            actorsIds.forEach { actorId ->
                database.moviesDao.insertJoin(MovieActorCrossRef(movieId, actorId))
            }
        }
    }
}

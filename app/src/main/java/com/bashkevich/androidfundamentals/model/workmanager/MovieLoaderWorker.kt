package com.bashkevich.androidfundamentals.model.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bashkevich.androidfundamentals.model.database.MoviesDatabase
import com.bashkevich.androidfundamentals.model.database.entity.GenreEntity
import com.bashkevich.androidfundamentals.model.database.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.database.entity.MovieGenreCrossRef
import com.bashkevich.androidfundamentals.model.network.RetrofitModule
import com.bashkevich.androidfundamentals.model.repository.toActorEntity
import com.bashkevich.androidfundamentals.model.repository.toGenreEntity
import com.bashkevich.androidfundamentals.model.repository.toMovieEntity
import com.bashkevich.androidfundamentals.moviesdetails.viewmodel.MoviesDetailsViewModel
import kotlinx.coroutines.*

class MovieLoaderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(
    appContext,
    params
) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = MoviesDatabase.getDatabase(applicationContext)
            val sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
            var nowPlayingMoviesIds: Deferred<List<Int>>
            var popularMoviesIds: Deferred<List<Int>>
            var topRatedMoviesIds: Deferred<List<Int>>
            var upcomingMoviesIds: Deferred<List<Int>>
            val genreEntities: Deferred<List<GenreEntity>>
            val moviesFromNetworkIds: List<Int>

            val prevMovies = database.moviesDao.getAllMovies()

            if (sharedPreferences.getInt(NEW_MOVIE_ID, 0) != 0) {
                val editor = sharedPreferences.edit()
                editor.remove(NEW_MOVIE_ID)
                editor.apply()
            }

            coroutineScope {
                genreEntities = async {
                    RetrofitModule.moviesApi.getGenres().genres.map { it.toGenreEntity() }
                }

                nowPlayingMoviesIds = async {
                    RetrofitModule.moviesApi.getNowPlayingMovies().results.map { it.id }
                }

                upcomingMoviesIds = async {
                    RetrofitModule.moviesApi.getUpcomingMovies().results.map { it.id }
                }

                popularMoviesIds = async {
                    RetrofitModule.moviesApi.getPopularMovies().results.map { it.id }
                }

                topRatedMoviesIds = async {
                    RetrofitModule.moviesApi.getTopRatedMovies().results.map { it.id }
                }

                moviesFromNetworkIds = topRatedMoviesIds.await().plus(popularMoviesIds.await())
                    .plus(upcomingMoviesIds.await())
                    .plus(nowPlayingMoviesIds.await()).distinct()

                launch {
                    database.moviesDao.insertAllGenres(genreEntities.await())
                }

                moviesFromNetworkIds.forEach { movieId ->

                    val movieDto = RetrofitModule.moviesApi.getMovieById(movieId)

                    val movieEntity = movieDto.toMovieEntity()

                    database.moviesDao.insertMovie(movieEntity)

                    launch {

                        val actorsEntities =
                            RetrofitModule.moviesApi.getActors(movieId).cast.filter { it.profilePicture != null }
                                .map { it.toActorEntity() }

                        database.moviesDao.insertAllActors(actorsEntities)

                        val actorsIds = actorsEntities.map { it.actorId }

                        actorsIds.forEach { actorId ->
                            database.moviesDao.insertMovieActorCrossRef(
                                MovieActorCrossRef(
                                    movieId,
                                    actorId
                                )
                            )
                        }
                    }

                    launch {
                        val genresIds = movieDto.genres?.map { it.id }

                        genresIds?.forEach { genreId ->
                            database.moviesDao.insertMovieGenreCrossRef(
                                MovieGenreCrossRef(
                                    movieId,
                                    genreId
                                )
                            )
                        }
                    }
                }

                if (prevMovies.isNotEmpty()) {

                    val prevMoviesIds = prevMovies.map { prevMovie -> prevMovie.movieId }

                    val moviesIdsToDelete = prevMoviesIds.minus(moviesFromNetworkIds)

                    moviesIdsToDelete.forEach { movieIdToDelete ->
                        database.moviesDao.deleteMovieById(movieIdToDelete)
                    }

                    val newMovie =
                        moviesFromNetworkIds.filterNot { it in prevMoviesIds }
                            .map { database.moviesDao.getMovieWithGenresAndActorsById(it) }
                            .maxByOrNull { it.movie.rating }

                    if (newMovie != null) {
                        val editor = sharedPreferences.edit()
                        editor.putInt(NEW_MOVIE_ID, newMovie.movie.movieId)
                        editor.apply()
                    }
                }
            }
            return@withContext Result.success()
        } catch (e: Exception) {
            Log.e(
                MoviesDetailsViewModel::class.java.simpleName,
                "Error in loading movies list  ${e.message}"
            )
            return@withContext Result.failure()
        }
    }

    companion object {
        const val UPDATE_MOVIES = "update_movies"
        const val NEW_MOVIE_ID = "new_movie_id"
    }
}
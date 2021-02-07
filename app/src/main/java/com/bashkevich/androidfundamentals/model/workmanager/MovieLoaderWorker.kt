package com.bashkevich.androidfundamentals.model.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bashkevich.androidfundamentals.model.database.MoviesDatabase
import com.bashkevich.androidfundamentals.model.database.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.database.entity.MovieGenreCrossRef
import com.bashkevich.androidfundamentals.model.network.RetrofitModule
import com.bashkevich.androidfundamentals.model.repository.toActorEntity
import com.bashkevich.androidfundamentals.model.repository.toGenreEntity
import com.bashkevich.androidfundamentals.model.repository.toMovieEntity
import com.bashkevich.androidfundamentals.moviesdetails.viewmodel.MoviesDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieLoaderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(
    appContext,
    params
) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = MoviesDatabase.getDatabase(applicationContext)
            var nowPlayingMoviesIds = listOf<Int>()
            var popularMoviesIds = listOf<Int>()
            var topRatedMoviesIds = listOf<Int>()
            var upcomingMoviesIds = listOf<Int>()
            val allMoviesIds: List<Int>

            coroutineScope {
                launch {
                    val genreEntities =
                        RetrofitModule.moviesApi.getGenres().genres.map { it.toGenreEntity() }

                    database.moviesDao.insertAllGenres(genreEntities)
                }

                launch {
                    nowPlayingMoviesIds =
                        RetrofitModule.moviesApi.getNowPlayingMovies().results.map { it.id }
                }

                launch {
                    upcomingMoviesIds =
                        RetrofitModule.moviesApi.getUpcomingMovies().results.map { it.id }
                }

                launch {
                    popularMoviesIds =
                        RetrofitModule.moviesApi.getPopularMovies().results.map { it.id }
                }

                launch {
                    topRatedMoviesIds =
                        RetrofitModule.moviesApi.getTopRatedMovies().results.map { it.id }
                }
            }

            allMoviesIds = topRatedMoviesIds.plus(popularMoviesIds).plus(upcomingMoviesIds)
                .plus(nowPlayingMoviesIds).distinct()

            allMoviesIds.forEach { movieId ->
                coroutineScope {

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
}
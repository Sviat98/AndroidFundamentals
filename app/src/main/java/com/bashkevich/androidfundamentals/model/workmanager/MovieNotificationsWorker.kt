package com.bashkevich.androidfundamentals.model.workmanager

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bashkevich.androidfundamentals.MainActivity
import com.bashkevich.androidfundamentals.model.database.MoviesDatabase
import com.bashkevich.androidfundamentals.model.database.entity.MovieWithGenresAndActors
import com.bashkevich.androidfundamentals.model.notifications.MovieNotifications
import com.bashkevich.androidfundamentals.model.repository.fromMovieWithGenresAndActors
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import com.bashkevich.androidfundamentals.moviesdetails.viewmodel.MoviesDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieNotificationsWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        try {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val movieNotifications = MovieNotifications(applicationContext)
            var movie: Movie? = null
            movieNotifications.initialize()

            withContext(Dispatchers.IO) {
                val movieId = sharedPreferences.getInt(MovieLoaderWorker.NEW_MOVIE_ID, 0)
                if (movieId != 0) {
                    movie =
                        MoviesDatabase.getDatabase(applicationContext).moviesDao.getMovieWithGenresAndActorsById(
                            movieId
                        ).fromMovieWithGenresAndActors()
                }

                if (sharedPreferences.getInt(MovieLoaderWorker.NEW_MOVIE_ID, 0) != 0) {
                    val editor = sharedPreferences.edit()
                    editor.remove(MovieLoaderWorker.NEW_MOVIE_ID)
                    editor.apply()
                }

            }

            if (movie != null) {
                movieNotifications.showMovieNotification(movie!!)
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e(
                MoviesDetailsViewModel::class.java.simpleName,
                "Error in showing notification  ${e.message}"
            )
            return Result.failure()
        }
    }

    companion object {
        const val NOTIFY = "notify_user"
    }
}
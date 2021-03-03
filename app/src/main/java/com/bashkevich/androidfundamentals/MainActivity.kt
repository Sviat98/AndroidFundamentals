package com.bashkevich.androidfundamentals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.bashkevich.androidfundamentals.model.database.MoviesDatabase
import com.bashkevich.androidfundamentals.model.database.entity.MovieEntity
import com.bashkevich.androidfundamentals.model.workmanager.MovieLoaderWorker
import com.bashkevich.androidfundamentals.model.workmanager.MovieNotificationsWorker
import com.bashkevich.androidfundamentals.model.workmanager.WorkerRepository
import com.bashkevich.androidfundamentals.moviesdetails.view.FragmentMoviesDetails
import com.bashkevich.androidfundamentals.movieslist.view.FragmentMoviesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val workerRepository = WorkerRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.main_container, FragmentMoviesList())
            }
            intent?.let(::handleMovieIntent)

            lifecycleScope.launch {

                WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                    MovieLoaderWorker.UPDATE_MOVIES,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workerRepository.periodicMovieRequest
                )
                WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                    MovieNotificationsWorker.NOTIFY,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workerRepository.periodicNotificationRequest
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleMovieIntent(intent)
        }
    }

    private fun handleMovieIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    openMovie(id)
                }
            }
        }
    }

    private fun openMovie(id: Int) {
        supportFragmentManager.popBackStack(
            FRAGMENT_MOVIE,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        supportFragmentManager.commit {
            addToBackStack(FRAGMENT_MOVIE)
            replace(R.id.main_container, FragmentMoviesDetails.newInstance(id))
        }
    }

    companion object {
        private const val FRAGMENT_MOVIE = "movie"
    }

}
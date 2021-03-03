package com.bashkevich.androidfundamentals.model.workmanager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

class WorkerRepository {
    private val constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()

    val periodicMovieRequest =
        PeriodicWorkRequest.Builder(MovieLoaderWorker::class.java, REPEAT_INTERVAL, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

    val periodicNotificationRequest =
        PeriodicWorkRequest.Builder(
            MovieNotificationsWorker::class.java,
            REPEAT_INTERVAL,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(NOTIFICATION_INITIAL_DELAY, TimeUnit.MINUTES)
            .build()

    companion object {
        const val REPEAT_INTERVAL = 12L
        const val NOTIFICATION_INITIAL_DELAY = 5L
    }

}
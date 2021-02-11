package com.bashkevich.androidfundamentals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.bashkevich.androidfundamentals.model.workmanager.MovieLoaderWorker
import com.bashkevich.androidfundamentals.movieslist.view.FragmentMoviesList
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentMoviesList>(R.id.main_container)
            }
        }

        lifecycleScope.launchWhenCreated {
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()

            val periodicMovieRequest =
                PeriodicWorkRequest.Builder(MovieLoaderWorker::class.java, 8, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(applicationContext).enqueue(periodicMovieRequest)
        }
    }

}
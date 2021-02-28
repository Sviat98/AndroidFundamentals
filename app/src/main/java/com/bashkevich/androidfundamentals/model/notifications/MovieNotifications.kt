package com.bashkevich.androidfundamentals.model.notifications

import android.app.PendingIntent
import androidx.core.app.NotificationChannelCompat
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.ImageRequest
import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import java.net.URI
import java.net.URL


class MovieNotifications(private val appContext: Context) {
    private val notificationManagerCompat = NotificationManagerCompat.from(appContext)

    fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {

            val notificationChannel =
                NotificationChannelCompat.Builder(CHANNEL_NEW_MOVIES, IMPORTANCE_HIGH)
                    .setName(appContext.getString(R.string.notification_channel_name))
                    .setDescription(
                        appContext.getString(
                            R.string.notification_channel_description
                        )
                    ).build()

            notificationManagerCompat.createNotificationChannel(notificationChannel)
        }
    }

    fun showMovieNotification(movie: Movie) {
        val contentUri = "$BASE_CONTENT_URI${movie.id}".toUri()

        val movieIntent = Intent().setAction(Intent.ACTION_VIEW).setData(contentUri)

        val pendingIntent = PendingIntent.getActivity(
            appContext,
            REQUEST_CODE,
            movieIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bitmap =
            BitmapFactory.decodeStream(URL(movie.poster).openConnection().getInputStream())

        val notification = NotificationCompat.Builder(appContext, CHANNEL_NEW_MOVIES)
            .setSmallIcon(R.drawable.ic_baseline_local_movies_24)
            .setLargeIcon(bitmap)
            .setContentTitle(movie.title)
            .setContentText(appContext.getString(R.string.notification_description, movie.ratings))
            .setPriority(NotificationCompat.PRIORITY_HIGH).setWhen(System.currentTimeMillis())
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)
            )
            .setAutoCancel(true).setContentIntent(pendingIntent)

        notificationManagerCompat.notify(MOVIE_TAG, movie.id, notification.build())
    }

    companion object {
        private const val CHANNEL_NEW_MOVIES = "new movies"
        private const val BASE_CONTENT_URI = "https://movies.bashkevich.by/movie/"
        private const val REQUEST_CODE = 111
        private const val MOVIE_TAG = "movie"
    }

}




package com.bashkevich.androidfundamentals.model

import android.content.Context
import androidx.lifecycle.Transformations
import com.bashkevich.androidfundamentals.model.dto.ActorDto
import com.bashkevich.androidfundamentals.model.dto.MovieDto
import com.bashkevich.androidfundamentals.model.entity.ActorEntity
import com.bashkevich.androidfundamentals.model.entity.MovieActorCrossRef
import com.bashkevich.androidfundamentals.model.entity.MovieEntity
import com.bashkevich.androidfundamentals.model.entity.MovieWithActors
import com.bashkevich.androidfundamentals.model.viewobject.Actor
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(applicationContext: Context) {
    val database: MoviesDatabase = MoviesDatabase.create(applicationContext)

    fun getAllMovies() =
        Transformations.map(database.moviesDao.getAllMoviesAndUpdate()) { entities ->
            entities.map { it.fromMovieEntity() }
        }

    suspend fun findMovieById(movieId: Int) = withContext(Dispatchers.IO) {
        database.moviesDao.getMovieWithActorsById(movieId).fromMovieWithActors()
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
                RetrofitModule.moviesApi.getMovieById(movieId).toMovieEntity()

            database.moviesDao.insertMovie(movieEntity)

            val actorsEntities =
                RetrofitModule.moviesApi.getActors(movieId).cast.filter { it.profilePicture != null }
                    .map { it.toActorEntity() }

            database.moviesDao.insertAllActors(actorsEntities)

            val actorsIds = actorsEntities.map { it.actorId }

            actorsIds.forEach { actorId ->
                database.moviesDao.insertJoin(MovieActorCrossRef(movieId, actorId))
            }
        }
    }
}

private fun MovieDto.toMovieEntity() =
    MovieEntity(
        movieId = this.id,
        title = this.title,
        posterPath = makeImageUrl(this.posterPicture),
        backdropPath = makeImageUrl(this.backdropPicture),
        runtime = this.runtime,
        genres = this.genres?.joinToString { it.name },
        rating = this.ratings,
        voteCount = this.votesCount,
        overview = this.overview,
        minimumAge = if (this.adult) 16 else 13
    )

private fun ActorDto.toActorEntity() = ActorEntity(
    actorId = this.id,
    profilePath = makeImageUrl(this.profilePicture),
    actorName = this.name
)

private fun MovieEntity.fromMovieEntity() = Movie(
    id = this.movieId,
    title = this.title,
    overview = this.overview,
    poster = this.posterPath,
    backdrop = this.backdropPath,
    ratings = this.rating,
    numberOfRatings = this.voteCount,
    minimumAge = this.minimumAge,
    runtime = this.runtime,
    genres = this.genres?.split(delimiters = charArrayOf(','))
)

private fun MovieWithActors.fromMovieWithActors() =
    this.movie.fromMovieEntity().copy(actors = this.actors.map { it.fromActorEntity() })


private fun ActorEntity.fromActorEntity() = Actor(
    id = this.actorId,
    picture = this.profilePath,
    name = this.actorName
)


private fun makeImageUrl(picturePath: String?) = "${RetrofitModule.IMAGES_BASE_URL}$picturePath"


package com.bashkevich.androidfundamentals.model.repository

import android.content.Context
import android.util.Log
import com.bashkevich.androidfundamentals.model.database.MoviesDatabase
import com.bashkevich.androidfundamentals.model.network.dto.ActorDto
import com.bashkevich.androidfundamentals.model.network.dto.GenreDto
import com.bashkevich.androidfundamentals.model.network.dto.MovieDto
import com.bashkevich.androidfundamentals.model.database.entity.*
import com.bashkevich.androidfundamentals.model.network.RetrofitModule
import com.bashkevich.androidfundamentals.model.viewobject.Actor
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class MoviesRepository(applicationContext: Context) {
    private val database: MoviesDatabase = MoviesDatabase.getDatabase(applicationContext)

    fun getAllMovies() =
        database.moviesDao.getAllMoviesWithGenresAndUpdate().map {
            it.map(MovieWithGenres::fromMovieWithGenres).toList()
        }.distinctUntilChanged()

    suspend fun findMovieById(movieId: Int) = withContext(Dispatchers.IO) {
        database.moviesDao.getMovieWithGenresAndActorsById(movieId).fromMovieWithGenresAndActors()
    }
}

fun MovieDto.toMovieEntity() =
    MovieEntity(
        movieId = this.id,
        title = this.title,
        posterPath = makeImageUrl(this.posterPicture),
        backdropPath = makeImageUrl(this.backdropPicture),
        runtime = this.runtime,
        rating = this.ratings,
        voteCount = this.votesCount,
        overview = this.overview,
        minimumAge = if (this.adult) 16 else 13
    )

fun ActorDto.toActorEntity() = ActorEntity(
    actorId = this.id,
    profilePath = makeImageUrl(this.profilePicture),
    actorName = this.name
)

fun MovieEntity.fromMovieEntity() = Movie(
    id = this.movieId,
    title = this.title,
    overview = this.overview,
    poster = this.posterPath,
    backdrop = this.backdropPath,
    ratings = this.rating,
    numberOfRatings = this.voteCount,
    minimumAge = this.minimumAge,
    runtime = this.runtime
)

fun MovieWithGenres.fromMovieWithGenres() =
    this.movie.fromMovieEntity().copy(genres = this.genres.map { it.fromGenreEntity() })

fun MovieWithGenresAndActors.fromMovieWithGenresAndActors() =
    this.movie.fromMovieEntity().copy(genres = this.genres.map { it.fromGenreEntity() })
        .copy(actors = this.actors.map { it.fromActorEntity() })


fun ActorEntity.fromActorEntity() = Actor(
    id = this.actorId,
    picture = this.profilePath,
    name = this.actorName
)

fun GenreEntity.fromGenreEntity() = this.genreName

fun GenreDto.toGenreEntity() = GenreEntity(this.id, this.name)

fun makeImageUrl(picturePath: String?) = "${RetrofitModule.IMAGES_BASE_URL}$picturePath"


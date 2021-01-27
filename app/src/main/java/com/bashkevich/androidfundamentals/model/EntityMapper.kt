package com.bashkevich.androidfundamentals.model

import com.bashkevich.androidfundamentals.model.dto.ActorDto
import com.bashkevich.androidfundamentals.model.dto.MovieDto
import com.bashkevich.androidfundamentals.model.entity.ActorEntity
import com.bashkevich.androidfundamentals.model.entity.MovieEntity
import com.bashkevich.androidfundamentals.model.entity.MovieWithActors
import com.bashkevich.androidfundamentals.model.viewobject.Actor
import com.bashkevich.androidfundamentals.model.viewobject.Movie

object EntityMapper {

    fun toMovieEntity(movieDto: MovieDto) =
        MovieEntity(
            movieId = movieDto.id,
            title = movieDto.title,
            posterPath = makeImageUrl(movieDto.posterPicture),
            backdropPath = makeImageUrl(movieDto.backdropPicture),
            runtime = movieDto.runtime,
            genres = movieDto.genres?.joinToString { it.name }?.filter { !it.isWhitespace() },
            rating = movieDto.ratings,
            voteCount = movieDto.votesCount,
            overview = movieDto.overview,
            minimumAge = if (movieDto.adult) 16 else 13
        )

    fun toActorEntity(actorDto: ActorDto) = ActorEntity(
        actorId = actorDto.id,
        profilePath = makeImageUrl(actorDto.profilePicture),
        actorName = actorDto.name
    )

    fun fromMovieEntity(movieEntity: MovieEntity) = Movie(
        id = movieEntity.movieId,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.posterPath,
        backdrop = movieEntity.backdropPath,
        ratings = movieEntity.rating,
        numberOfRatings = movieEntity.voteCount,
        minimumAge = movieEntity.minimumAge,
        runtime = movieEntity.runtime,
        genres = movieEntity.genres?.split(delimiters = charArrayOf(','))
    )

    fun fromMovieWithActors(movieWithActors: MovieWithActors) =
        fromMovieEntity(movieWithActors.movie).copy(actors = movieWithActors.actors.map {
            fromActorEntity(it)
        })

    private fun fromActorEntity(actorEntity: ActorEntity) = Actor(
        id = actorEntity.actorId,
        picture = actorEntity.profilePath,
        name = actorEntity.actorName
    )


    private fun makeImageUrl(picturePath: String?) = "${RetrofitModule.IMAGES_BASE_URL}$picturePath"
}
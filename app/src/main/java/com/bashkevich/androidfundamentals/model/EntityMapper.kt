package com.bashkevich.androidfundamentals.model

import com.bashkevich.androidfundamentals.model.dto.ActorDto
import com.bashkevich.androidfundamentals.model.dto.GenreDto
import com.bashkevich.androidfundamentals.model.dto.MovieDto
import com.bashkevich.androidfundamentals.model.entity.Actor
import com.bashkevich.androidfundamentals.model.entity.Genre
import com.bashkevich.androidfundamentals.model.entity.Movie

object EntityMapper {
    fun convertToMovieFromDto(movieDto: MovieDto): Movie {

        return Movie(
            id = movieDto.id,
            title = movieDto.title,
            overview = movieDto.overview,
            poster = movieDto.posterPicture,
            backdrop = movieDto.backdropPicture,
            ratings = movieDto.ratings,
            numberOfRatings = movieDto.votesCount,
            minimumAge = if (movieDto.adult) 16 else 13,
            runtime = movieDto.runtime,
            genres = movieDto.genres?.map { convertGenreFromDto(it) }
        )
    }

    private fun convertGenreFromDto(genreDto: GenreDto) = Genre(genreDto.id, genreDto.name)

    fun convertActorFromDto(actorDto: ActorDto) =
         Actor(actorDto.id, actorDto.name, actorDto.profilePicture)
}
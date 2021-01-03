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
            movieDto.id,
            movieDto.title,
            movieDto.overview,
            movieDto.posterPicture?.let { it },
            movieDto.backdropPicture?.let { it },
            movieDto.ratings,
            movieDto.votesCount,
            if (movieDto.adult) 16 else 13,
            movieDto.runtime?.let { it },
            movieDto.genres?.map { convertGenreFromDto(it) },
            listOf<Actor>()
        )
    }

    private fun convertGenreFromDto(genreDto: GenreDto) = Genre(genreDto.id, genreDto.name)

    fun convertActorFromDto(actorDto: ActorDto) =
        actorDto.profilePicture?.let { Actor(actorDto.id, actorDto.name, it) }
}
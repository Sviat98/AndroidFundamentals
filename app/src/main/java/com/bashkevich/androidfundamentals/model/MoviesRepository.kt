package com.bashkevich.androidfundamentals.model

import com.bashkevich.androidfundamentals.model.entity.Movie

class MoviesRepository {

    suspend fun getTopRatedMovies() = RetrofitModule.moviesApi.getTopRatedMovies()
    suspend fun getNowPlayingMovies() = RetrofitModule.moviesApi.getNowPlayingMovies()
    suspend fun getUpcomingMovies() = RetrofitModule.moviesApi.getUpcomingMovies()
    suspend fun getPopularMovies() = RetrofitModule.moviesApi.getPopularMovies()
    suspend fun getAllMovies(moviesIds : List<Int>) = moviesIds.map {
        EntityMapper.convertToMovieFromDto(RetrofitModule.moviesApi.getMovieById(it))
    }
    suspend fun getActors(movieId: Int) = RetrofitModule.moviesApi.getActors(movieId).cast.filter { it.profilePicture != null }
        .map { EntityMapper.convertActorFromDto(it) }

    companion object{
        var allMovies: List<Movie>? = null
    }
}
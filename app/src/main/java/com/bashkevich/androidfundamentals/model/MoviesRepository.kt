package com.bashkevich.androidfundamentals.model

class MoviesRepository {
    suspend fun getTopRatedMovies() = RetrofitModule.moviesApi.getTopRatedMovies()
    suspend fun getNowPlayingMovies() = RetrofitModule.moviesApi.getNowPlayingMovies()
    suspend fun getUpcomingMovies() = RetrofitModule.moviesApi.getUpcomingMovies()
    suspend fun getPopularMovies() = RetrofitModule.moviesApi.getPopularMovies()
    suspend fun getMovieById(id: Int) = RetrofitModule.moviesApi.getMovieById(id)
    suspend fun getActors(movieId: Int) = RetrofitModule.moviesApi.getActors(movieId)
}
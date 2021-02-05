package com.bashkevich.androidfundamentals.model.network

import com.bashkevich.androidfundamentals.model.network.dto.CreditsResponse
import com.bashkevich.androidfundamentals.model.network.dto.GenresResponse
import com.bashkevich.androidfundamentals.model.network.dto.MovieDto
import com.bashkevich.androidfundamentals.model.network.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path(value = "movie_id") id: Int): CreditsResponse

    @GET("movie/{id}")
    suspend fun getMovieById(@Path(value = "id") id: Int): MovieDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse
}
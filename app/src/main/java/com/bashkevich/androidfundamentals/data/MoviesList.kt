package com.bashkevich.androidfundamentals.data

import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.model.Movie

class MoviesList {
    fun getMovies() : List<Movie> = listOf(
        Movie(13, R.drawable.movie_poster,"Avengers: End Game","Genres",4.0,125,137),
        Movie(13, R.drawable.movie_poster,"Avengers: End Game","Genres",4.0,125,137),
        Movie(13, R.drawable.movie_poster,"Avengers: End Game","Genres",4.0,125,137),
        Movie(13, R.drawable.movie_poster,"Avengers: End Game","Genres",4.0,125,137)

    )

}
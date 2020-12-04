package com.bashkevich.androidfundamentals.data

import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.model.Actor
import com.bashkevich.androidfundamentals.model.Movie

class DataList {
    fun getMovies() : List<Movie> = listOf(
        Movie(13, R.drawable.avengers,"Avengers: End Game","Action, Adventure, Fantasy",4.0,125,137),
        Movie(16, R.drawable.tenet,"Tenet","Action, Sci-Fi, Thriller",5.0,98,97),
        Movie(13, R.drawable.black_widow,"Black Widow","Action, Adventure, Sci-Fi",4.0,38,102),
        Movie(13, R.drawable.wonder_woman,"Wonder Woman 1984","Action, Adventure, Fantasy",5.0,74,120)

    )

    fun getActors() : List<Actor> = listOf(
        Actor(R.drawable.robert_downey_jr,"Robert Downey Jr."),
        Actor(R.drawable.chris_evans,"Chris Evans"),
        Actor(R.drawable.mark_ruffalo,"Mark Ruffalo"),
        Actor(R.drawable.chris_hemsworth,"Chris Hemsworth"),
    )

}
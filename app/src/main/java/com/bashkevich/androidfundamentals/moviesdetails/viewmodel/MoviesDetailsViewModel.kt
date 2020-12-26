package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.data.JsonLoad
import com.bashkevich.androidfundamentals.data.Movie
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(
        private val jsonLoad: JsonLoad
) : ViewModel() {
    private var _movieLiveData = MutableLiveData<Movie>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    fun getMovieFromList(movieId: Int) {
        if (_movieLiveData.value?.id != movieId) {
            viewModelScope.launch {
                val movies = jsonLoad.loadMovies()
                val movie = movies.find { movie -> movie.id == movieId }

                _movieLiveData.value = movie
            }
        }
    }

}
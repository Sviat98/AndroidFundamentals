package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.model.MoviesRepository
import com.bashkevich.androidfundamentals.model.entity.Actor
import com.bashkevich.androidfundamentals.model.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private var _movieLiveData = MutableLiveData<Movie>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    fun loadMovie(movieId: Int) {
        if (_movieLiveData.value?.id != movieId) {
            viewModelScope.launch {

                val movieWithActors = moviesRepository.findMovieById(movieId)
                    ?.copy(actors = addActorsToMovie(movieId))

                _movieLiveData.value = movieWithActors
            }
        }
    }

    private suspend fun addActorsToMovie(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        moviesRepository.getActors(movieId)
    }

}
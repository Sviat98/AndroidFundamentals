package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.model.MoviesRepository
import com.bashkevich.androidfundamentals.model.viewobject.Actor
import com.bashkevich.androidfundamentals.model.viewobject.Movie
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
                try {
                    val movieWithActors = moviesRepository.findMovieById(movieId)
                    _movieLiveData.value = movieWithActors
                } catch (e: Exception) {
                    Log.e(
                        MoviesDetailsViewModel::class.java.simpleName,
                        "Error in loading movie with id = $movieId ${e.message}"
                    )
                }
            }
        }
    }

}
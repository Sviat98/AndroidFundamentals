package com.bashkevich.androidfundamentals.movieslist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.bashkevich.androidfundamentals.model.repository.MoviesRepository
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private val _moviesListLiveData = MutableLiveData<List<Movie>>()

    var moviesListLiveData: LiveData<List<Movie>>? = null

    private val _errorLiveData = MutableLiveData<String>()

    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun loadMoviesList() {
        viewModelScope.launch {
            try {
                moviesListLiveData = moviesRepository.getAllMovies().asLiveData()

            } catch (e: Exception) {
                Log.e(
                    MoviesListViewModel::class.java.simpleName,
                    "Error in loading movies list ${e.message}"
                )
                _errorLiveData.value = "Error in loading movies list"
            }
        }
    }

}
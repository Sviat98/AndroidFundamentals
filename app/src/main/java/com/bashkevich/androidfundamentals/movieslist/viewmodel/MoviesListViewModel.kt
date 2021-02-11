package com.bashkevich.androidfundamentals.movieslist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.bashkevich.androidfundamentals.model.repository.MoviesRepository
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private val _moviesListLiveData = MutableLiveData<List<Movie>>()

    val moviesListLiveData: LiveData<List<Movie>>
        get() = _moviesListLiveData

    private val _errorLiveData = MutableLiveData<String>()

    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun loadMoviesList() {
        viewModelScope.launch {
            try {
                _moviesListLiveData.value = moviesRepository.getAllMovies().asLiveData().value
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
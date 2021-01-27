package com.bashkevich.androidfundamentals.movieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.model.MoviesRepository
import com.bashkevich.androidfundamentals.model.viewobject.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesListLiveData: LiveData<List<Movie>> = moviesRepository.getAllMovies()


    fun loadMoviesList() {
            viewModelScope.launch {
                 moviesRepository.refreshMovies()
        }
    }

}
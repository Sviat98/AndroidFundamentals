package com.bashkevich.androidfundamentals.movieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.data.JsonLoad
import com.bashkevich.androidfundamentals.data.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val jsonLoad: JsonLoad
) : ViewModel() {
    private val _moviesListLiveData = MutableLiveData<List<Movie>>()

    val moviesListLiveData : LiveData<List<Movie>>
    get() = _moviesListLiveData



    fun loadMoviesList(){
        viewModelScope.launch {
                val movies = jsonLoad.loadMovies()
                _moviesListLiveData.value = movies
            }
    }
}
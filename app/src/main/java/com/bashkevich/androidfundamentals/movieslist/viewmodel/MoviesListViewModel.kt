package com.bashkevich.androidfundamentals.movieslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.model.EntityMapper
import com.bashkevich.androidfundamentals.model.MoviesRepository
import com.bashkevich.androidfundamentals.model.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private val _moviesListLiveData = MutableLiveData<List<Movie>>()


    val moviesListLiveData: LiveData<List<Movie>>
        get() = _moviesListLiveData


    fun loadMoviesList() {
        if (_moviesListLiveData.value == null) {
            viewModelScope.launch {


                val movies = loadMoviesFromNetwork()


                _moviesListLiveData.value = movies
            }
        }
    }

    private suspend fun loadMoviesFromNetwork() = withContext(Dispatchers.IO) {
        val topRatedMoviesIds =
            moviesRepository.getTopRatedMovies().results.map { it.id }.toMutableList()
        val popularMoviesIds =
            moviesRepository.getPopularMovies().results.map { it.id }.toMutableList()
        val upcomingMoviesIds =
            moviesRepository.getUpcomingMovies().results.map { it.id }.toMutableList()
        val nowPlayingMoviesIds =
            moviesRepository.getNowPlayingMovies().results.map { it.id }.toMutableList()

        var allMoviesIds = mutableListOf<Int>()

        allMoviesIds.addAll(topRatedMoviesIds)
        allMoviesIds.addAll(popularMoviesIds)
        allMoviesIds.addAll(upcomingMoviesIds)
        allMoviesIds.addAll(nowPlayingMoviesIds)


        allMoviesIds.map {
            EntityMapper.convertToMovieFromDto(moviesRepository.getMovieById(it))
        }

    }
}
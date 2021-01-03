package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashkevich.androidfundamentals.model.EntityMapper
import com.bashkevich.androidfundamentals.model.MoviesRepository
import com.bashkevich.androidfundamentals.model.RetrofitModule
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

    fun loadMovie(movie: Movie) {
        if (_movieLiveData.value?.id != movie.id) {
            viewModelScope.launch {

                movie.actors = addActorsToMovie(movie.id)

                _movieLiveData.value = movie
            }
        }
    }

    private suspend fun addActorsToMovie(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        moviesRepository.getActors(movieId).cast.filter { it.profilePicture != null }
            .map { EntityMapper.convertActorFromDto(it) } as List<Actor>
    }

}
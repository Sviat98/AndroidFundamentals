package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bashkevich.androidfundamentals.model.MoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(MoviesRepository())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
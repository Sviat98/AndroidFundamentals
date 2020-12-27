package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bashkevich.androidfundamentals.data.JsonLoad

@Suppress("UNCHECKED_CAST")
class MoviesDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(JsonLoad())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
package com.bashkevich.androidfundamentals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MoviesDetailsViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
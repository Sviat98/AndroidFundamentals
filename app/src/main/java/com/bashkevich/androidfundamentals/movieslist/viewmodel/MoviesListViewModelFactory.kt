package com.bashkevich.androidfundamentals.movieslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bashkevich.androidfundamentals.data.JsonLoad

@Suppress("UNCHECKED_CAST")
class MoviesListViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(JsonLoad())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
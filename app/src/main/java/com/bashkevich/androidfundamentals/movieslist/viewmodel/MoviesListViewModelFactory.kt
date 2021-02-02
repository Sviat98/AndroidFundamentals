package com.bashkevich.androidfundamentals.movieslist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bashkevich.androidfundamentals.model.MoviesDatabase
import com.bashkevich.androidfundamentals.model.MoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesListViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(MoviesRepository(applicationContext))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
package com.bashkevich.androidfundamentals.moviesdetails.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bashkevich.androidfundamentals.model.repository.MoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesDetailsViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(
            MoviesRepository(
                applicationContext
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
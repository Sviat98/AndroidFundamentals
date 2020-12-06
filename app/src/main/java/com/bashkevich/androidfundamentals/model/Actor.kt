package com.bashkevich.androidfundamentals.model

import androidx.annotation.DrawableRes

data class Actor(
    @DrawableRes
    val  imageResource: Int,
    val name : String
)
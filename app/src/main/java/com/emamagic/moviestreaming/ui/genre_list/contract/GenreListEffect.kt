package com.emamagic.moviestreaming.ui.genre_list.contract

import androidx.annotation.IdRes
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class GenreListEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int? = null): GenreListEffect()
    data class Navigate(@IdRes val destination: Int): GenreListEffect()
    data class Loading(val isLoading: Boolean): GenreListEffect()
}
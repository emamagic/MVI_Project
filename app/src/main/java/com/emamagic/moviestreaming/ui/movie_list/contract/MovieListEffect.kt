package com.emamagic.moviestreaming.ui.movie_list.contract

import androidx.annotation.IdRes
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class MovieListEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): MovieListEffect()
    data class Navigate(@IdRes val destination: Int): MovieListEffect()
    data class Loading(val isLoading: Boolean): MovieListEffect()
}
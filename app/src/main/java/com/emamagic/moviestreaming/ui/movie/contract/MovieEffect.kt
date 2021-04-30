package com.emamagic.moviestreaming.ui.movie.contract

import androidx.annotation.IdRes
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class MovieEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): MovieEffect()
    data class Navigate(@IdRes val destination: Int): MovieEffect()
    data class Loading(val isLoading: Boolean): MovieEffect()
}
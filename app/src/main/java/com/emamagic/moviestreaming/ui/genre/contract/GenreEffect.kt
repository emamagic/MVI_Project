package com.emamagic.moviestreaming.ui.genre.contract

import androidx.annotation.IdRes
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class GenreEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int? = null): GenreEffect()
    data class Navigate(@IdRes val destination: Int): GenreEffect()
}
package com.emamagic.moviestreaming.ui.movie.contract

import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class MovieEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): MovieEffect()
}
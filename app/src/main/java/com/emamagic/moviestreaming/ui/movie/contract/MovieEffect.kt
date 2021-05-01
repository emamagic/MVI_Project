package com.emamagic.moviestreaming.ui.movie.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class MovieEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): MovieEffect()
    data class Loading(val isLoading: Boolean): MovieEffect()
    data class Navigate(val navDirections: NavDirections): MovieEffect()
}
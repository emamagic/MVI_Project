package com.emamagic.moviestreaming.ui.genre_type.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class GenreTypeEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int? = null): GenreTypeEffect()
    data class Navigate(val navDirections: NavDirections): GenreTypeEffect()
    data class Loading(val isLoading: Boolean): GenreTypeEffect()
}
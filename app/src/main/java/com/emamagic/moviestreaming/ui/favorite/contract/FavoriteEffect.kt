package com.emamagic.moviestreaming.ui.favorite.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class FavoriteEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): FavoriteEffect()
    data class Loading(val isLoading: Boolean): FavoriteEffect()
    data class Navigate(val navDirections: NavDirections): FavoriteEffect()
}
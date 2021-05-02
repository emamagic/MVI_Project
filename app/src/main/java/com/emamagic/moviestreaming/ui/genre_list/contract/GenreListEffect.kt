package com.emamagic.moviestreaming.ui.genre_list.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class GenreListEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int? = null): GenreListEffect()
    data class Navigate(val navDirections: NavDirections): GenreListEffect()
    data class Loading(val isLoading: Boolean): GenreListEffect()
}
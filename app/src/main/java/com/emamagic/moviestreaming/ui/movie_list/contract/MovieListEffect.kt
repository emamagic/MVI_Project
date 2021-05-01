package com.emamagic.moviestreaming.ui.movie_list.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.util.ToastyMode

sealed class MovieListEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): MovieListEffect()
    data class Navigate(val navDirect: NavDirections): MovieListEffect()
    data class Loading(val isLoading: Boolean): MovieListEffect()
}
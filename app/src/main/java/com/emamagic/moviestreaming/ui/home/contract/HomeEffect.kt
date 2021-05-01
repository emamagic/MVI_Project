package com.emamagic.moviestreaming.ui.home.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class HomeEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int? = null): HomeEffect()
    data class Navigate(val navDirect: NavDirections): HomeEffect()
    data class Loading(val isLoading: Boolean): HomeEffect()
    object DisableRefreshing: HomeEffect()
}
package com.emamagic.moviestreaming.ui.intro.splash.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class SplashEffect: BaseEffect {
    data class Loading(val isLoading: Boolean): SplashEffect()
    data class ShowToast(val message: String, @ToastyMode val mode: Int): SplashEffect()
    data class Navigate(val navDirections: NavDirections): SplashEffect()
}
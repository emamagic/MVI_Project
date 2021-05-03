package com.emamagic.moviestreaming.ui.intro.intro.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class IntroEffect: BaseEffect {
    data class Loading(val isLoading: Boolean): IntroEffect()
    data class ShowToast(val message: String, @ToastyMode val mode: Int): IntroEffect()
    data class Navigate(val navDirections: NavDirections): IntroEffect()
}
package com.emamagic.moviestreaming.base

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.util.ToastyMode

sealed class CommonEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): CommonEffect()
    data class Loading(val isLoading: Boolean, val isDim: Boolean = true): CommonEffect()
    data class Navigate(val navDirections: NavDirections? = null): CommonEffect()
}

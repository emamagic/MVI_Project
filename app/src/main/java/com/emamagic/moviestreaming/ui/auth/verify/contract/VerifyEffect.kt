package com.emamagic.moviestreaming.ui.auth.verify.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class VerifyEffect: BaseEffect {
    data class Loading(val isLoading: Boolean): VerifyEffect()
    data class ShowToast(val message: String, @ToastyMode val mode: Int): VerifyEffect()
    data class Navigate(val navDirections: NavDirections): VerifyEffect()
}
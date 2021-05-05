package com.emamagic.moviestreaming.ui.auth.register.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class RegisterEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): RegisterEffect()
    data class Loading(val isLoading: Boolean): RegisterEffect()
    data class Navigate(val navDirections: NavDirections?): RegisterEffect()
}
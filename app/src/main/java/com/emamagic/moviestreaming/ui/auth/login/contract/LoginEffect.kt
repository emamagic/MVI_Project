package com.emamagic.moviestreaming.ui.auth.login.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class LoginEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): LoginEffect()
    data class Loading(val isLoading: Boolean ,val isDim: Boolean = false): LoginEffect()
    data class Navigate(val navDirections: NavDirections): LoginEffect()
}

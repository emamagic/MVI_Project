package com.emamagic.moviestreaming.ui.home.contract

import androidx.annotation.IdRes
import com.emamagic.moviestreaming.base.BaseEffect

sealed class HomeEffect: BaseEffect {
    data class ShowSnackBar(val message: String): HomeEffect()
    data class ShowToast(val message: String): HomeEffect()
    data class Navigate(@IdRes val destination: Int): HomeEffect()
}
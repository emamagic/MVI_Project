package com.emamagic.moviestreaming.ui.search_type.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class SearchTypeEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): SearchTypeEffect()
    data class Loading(val isLoading: Boolean): SearchTypeEffect()
    data class Navigate(val navDirections: NavDirections): SearchTypeEffect()
}
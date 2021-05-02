package com.emamagic.moviestreaming.ui.search.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class SearchEffect: BaseEffect {
    data class ShowToast(val message: String ,@ToastyMode val mode: Int): SearchEffect()
    data class Loading(val isLoading: Boolean): SearchEffect()
    data class Navigate(val navDirections: NavDirections): SearchEffect()
}

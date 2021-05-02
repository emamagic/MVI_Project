package com.emamagic.moviestreaming.ui.episode_list.contract

import androidx.navigation.NavDirections
import com.emamagic.moviestreaming.base.BaseEffect
import com.emamagic.moviestreaming.util.ToastyMode

sealed class EpisodeListEffect: BaseEffect {
    data class ShowToast(val message: String, @ToastyMode val mode: Int): EpisodeListEffect()
    data class Navigate(val navDirections: NavDirections): EpisodeListEffect()
    data class Loading(val isLoading: Boolean): EpisodeListEffect()
}

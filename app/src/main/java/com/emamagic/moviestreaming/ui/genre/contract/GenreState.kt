package com.emamagic.moviestreaming.ui.genre.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState

data class GenreState(
    val isLoading: Boolean,
    @CurrentGenreState val currentState: Int
) : BaseState {

    companion object {
        fun initialize(): GenreState =
            GenreState(
                isLoading = false,
                currentState = CurrentGenreState.NON_STATE
            )
    }
}


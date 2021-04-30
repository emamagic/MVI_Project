package com.emamagic.moviestreaming.ui.genre.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState

data class GenreState(
    val genreList: List<GenreEntity>,
    @CurrentGenreState val currentState: Int
) : BaseState {

    companion object {
        fun initialize(): GenreState =
            GenreState(
                emptyList(),
                currentState = CurrentGenreState.NON_STATE
            )
    }
}


package com.emamagic.moviestreaming.ui.genre_type.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.GenreEntity

data class GenreTypeState(
    val genreList: List<GenreEntity>,
    @CurrentGenreTypeState val currentState: Int
) : BaseState {

    companion object {
        fun initialize(): GenreTypeState =
            GenreTypeState(
                emptyList(),
                currentState = CurrentGenreTypeState.NON_STATE
            )
    }
}


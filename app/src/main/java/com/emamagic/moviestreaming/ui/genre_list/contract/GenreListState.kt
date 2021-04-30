package com.emamagic.moviestreaming.ui.genre_list.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.GenreEntity

data class GenreListState(
    val genreList: List<GenreEntity>,
    @CurrentGenreState val currentState: Int
) : BaseState {

    companion object {
        fun initialize(): GenreListState =
            GenreListState(
                emptyList(),
                currentState = CurrentGenreState.NON_STATE
            )
    }
}


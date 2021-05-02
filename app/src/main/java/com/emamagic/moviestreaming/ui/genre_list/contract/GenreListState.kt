package com.emamagic.moviestreaming.ui.genre_list.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.MovieEntity

data class GenreListState(
    @CurrentGenreListState val currentState: Int,
    val genres: List<MovieEntity>
): BaseState {
    companion object {
        fun initialize() =
            GenreListState(
                currentState = CurrentGenreListState.NON_STATE,
                emptyList()
            )
    }
}
package com.emamagic.moviestreaming.ui.modules.genre_list.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite

data class GenreListState(
    val genres: List<MovieWithFavorite>?
): BaseState {
    companion object {
        fun initialize() =
            GenreListState(
                emptyList()
            )
    }
}
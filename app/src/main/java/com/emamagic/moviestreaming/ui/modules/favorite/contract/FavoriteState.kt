package com.emamagic.moviestreaming.ui.modules.favorite.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.MovieEntity

data class FavoriteState(
    val movies: List<MovieEntity>
): BaseState {
    companion object{
        fun initialize() =
            FavoriteState(movies = emptyList())
    }
}
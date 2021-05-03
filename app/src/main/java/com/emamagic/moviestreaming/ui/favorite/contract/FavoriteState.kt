package com.emamagic.moviestreaming.ui.favorite.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.MovieEntity

data class FavoriteState(
    val movies: List<MovieEntity>
): BaseState {
    companion object{
        fun initialize() =
            FavoriteState(movies = emptyList())
    }
}
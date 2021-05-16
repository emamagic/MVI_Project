package com.emamagic.moviestreaming.ui.modules.movie_list.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite

data class MovieListState(
    @CurrentMovieListState val currentMovieState: Int,
    val movieList: List<MovieWithFavorite>
): BaseState {
    companion object {
        fun initialize() =
            MovieListState(
                currentMovieState = CurrentMovieListState.NON_STATE,
                movieList = emptyList()
            )
    }
}
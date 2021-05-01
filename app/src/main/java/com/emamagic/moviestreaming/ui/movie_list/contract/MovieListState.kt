package com.emamagic.moviestreaming.ui.movie_list.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.MovieEntity

data class MovieListState(
    @CurrentMovieListState val currentMovieState: Int,
    val movieList: List<MovieEntity>
): BaseState {
    companion object {
        fun initialize() =
            MovieListState(
                currentMovieState = CurrentMovieListState.NON_STATE,
                movieList = emptyList()
            )
    }
}
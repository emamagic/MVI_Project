package com.emamagic.moviestreaming.ui.movie.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.MovieEntity

data class MovieState(
    @CurrentMovieState val currentMovieState: Int,
    val movieList: List<MovieEntity>
): BaseState {
    companion object {
        fun initialize() =
            MovieState(
                currentMovieState = CurrentMovieState.NON_STATE,
                movieList = emptyList()
            )
    }
}
package com.emamagic.moviestreaming.ui.movie_list.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite

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
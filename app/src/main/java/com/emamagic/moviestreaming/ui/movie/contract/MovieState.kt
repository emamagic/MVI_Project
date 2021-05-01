package com.emamagic.moviestreaming.ui.movie.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity

data class MovieState(
    @CurrentMovieState val currentState: Int,
    val movie: MovieEntity,
    val casts: List<CastEntity>
): BaseState {

    companion object {
        fun initialize() =
            MovieState(
                CurrentMovieState.NON_STATE,
                MovieEntity(),
                emptyList()
            )
    }

}

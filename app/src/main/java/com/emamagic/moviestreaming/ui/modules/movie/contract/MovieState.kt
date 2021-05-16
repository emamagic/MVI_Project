package com.emamagic.moviestreaming.ui.modules.movie.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.CastEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity

data class MovieState(
    @CurrentMovieState val currentState: Int,
    val movie: MovieEntity,
    val casts: List<CastEntity>,
    val seasons: List<SeasonEntity>
): BaseState {

    companion object {
        fun initialize() =
            MovieState(
                CurrentMovieState.NON_STATE,
                MovieEntity(),
                emptyList(),
                emptyList()
            )
    }

}
